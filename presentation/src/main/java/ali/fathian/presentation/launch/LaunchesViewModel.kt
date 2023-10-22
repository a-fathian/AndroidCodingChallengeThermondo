package ali.fathian.presentation.launch

import ali.fathian.domain.common.Resource
import ali.fathian.domain.use_cases.BookmarksUseCase
import ali.fathian.domain.use_cases.GetAllLaunchesUseCase
import ali.fathian.presentation.common.DispatcherIO
import ali.fathian.presentation.model.Launches
import ali.fathian.presentation.model.Origin
import ali.fathian.presentation.model.UiModel
import ali.fathian.presentation.model.mapper.toDomainModel
import ali.fathian.presentation.model.mapper.toUiModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchUseCase: GetAllLaunchesUseCase,
    private val bookmarksUseCase: BookmarksUseCase,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(Launches())
    val uiState = _uiState.asStateFlow()

    private val _bookmarks = MutableStateFlow<List<UiModel>?>(null)
    val bookmarks = _bookmarks.asStateFlow()

    init {
        fetchBookmarks()
        uiState.combine(bookmarks) { allLaunches, bookmarks ->
            syncWithDatabase(allLaunches.allLaunches, bookmarks ?: emptyList())
        }.shareIn(viewModelScope, started = SharingStarted.Eagerly)
    }

    private fun fetchBookmarks() {
        viewModelScope.launch(dispatcher) {
            bookmarksUseCase.getLocalLaunches().collect {
                _bookmarks.emit(it.map { item -> item.toUiModel() })
            }
        }
    }

    fun fetchLaunches() {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(uiState.value.copy(loading = true))
            val launches = launchUseCase()
            if (launches is Resource.Success) {
                launches.data?.let {
                    _uiState.emit(
                        Launches(
                            allLaunches = it.map { item -> item.toUiModel() },
                            upcomingLaunches = it.map { item -> item.toUiModel() }
                                .filter { item -> item.upcoming },
                            pastLaunches = it.map { item -> item.toUiModel() }
                                .filter { item -> !item.upcoming },
                            errorMessage = "",
                            loading = false
                        )
                    )
                }
            } else {
                _uiState.emit(
                    Launches(errorMessage = launches.message ?: "Unknown Error", loading = false)
                )
            }
        }
    }

    private fun syncWithDatabase(allLaunches: List<UiModel>, bookmarks: List<UiModel>) {
        viewModelScope.launch(dispatcher) {
            val list = mutableListOf<UiModel>()
            allLaunches.forEach { uiModel ->
                if (bookmarks.any { it.id == uiModel.id }) {
                    list.add(uiModel.copy(bookmarked = true))
                } else {
                    list.add(uiModel.copy(bookmarked = false))
                }
            }
            _uiState.emit(
                uiState.value.copy(
                    allLaunches = list,
                    upcomingLaunches = list.filter { it.upcoming },
                    pastLaunches = list.filter { !it.upcoming },
                    errorMessage = "",
                    loading = allLaunches.isEmpty()
                )
            )
        }
    }

    fun onItemClick(uiModel: UiModel, origin: Origin) {
        when (origin) {
            Origin.AllLaunches -> {
                val previousExpandedItem = _uiState.value.allLaunches.find { it.expanded }
                if (uiModel != previousExpandedItem) {
                    val allLaunches = uiState.value.allLaunches
                    val newUiModel = allLaunches[allLaunches.indexOf(uiModel)].copy(expanded = true)
                    val newAllLaunches = allLaunches.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _uiState.value = uiState.value.copy(allLaunches = newAllLaunches)
                }
                if (previousExpandedItem != null) {
                    val allLaunches = uiState.value.allLaunches
                    val newUiModel =
                        allLaunches[allLaunches.indexOf(previousExpandedItem)].copy(expanded = false)
                    val newAllLaunches = allLaunches.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _uiState.value = uiState.value.copy(allLaunches = newAllLaunches)
                }
            }

            Origin.BookmarkLaunches -> {
                val previousExpandedItem = _bookmarks.value?.find { it.expanded }
                if (uiModel != previousExpandedItem) {
                    val allBookmarks = bookmarks.value ?: emptyList()
                    val newUiModel =
                        allBookmarks[allBookmarks.indexOf(uiModel)].copy(expanded = true)
                    val newAllBookmarks = allBookmarks.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _bookmarks.value = newAllBookmarks
                }
                if (previousExpandedItem != null) {
                    val allBookmarks = bookmarks.value ?: emptyList()
                    val newUiModel =
                        allBookmarks[allBookmarks.indexOf(uiModel)].copy(expanded = false)
                    val newAllBookmarks = allBookmarks.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _bookmarks.value = newAllBookmarks
                }
            }

            Origin.PastLaunches -> {
                val previousExpandedItem = _uiState.value.pastLaunches.find { it.expanded }
                if (uiModel != previousExpandedItem) {
                    val pastLaunches = uiState.value.allLaunches
                    val newUiModel =
                        pastLaunches[pastLaunches.indexOf(uiModel)].copy(expanded = true)
                    val newPastLaunches = pastLaunches.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _uiState.value = uiState.value.copy(pastLaunches = newPastLaunches)
                }
                if (previousExpandedItem != null) {
                    val pastLaunches = uiState.value.allLaunches
                    val newUiModel =
                        pastLaunches[pastLaunches.indexOf(previousExpandedItem)].copy(expanded = false)
                    val newPastLaunches = pastLaunches.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _uiState.value = uiState.value.copy(pastLaunches = newPastLaunches)
                }
            }

            Origin.UpcomingLaunches -> {
                val previousExpandedItem = _uiState.value.upcomingLaunches.find { it.expanded }
                if (uiModel != previousExpandedItem) {
                    val upcomingLaunches = uiState.value.allLaunches
                    val newUiModel =
                        upcomingLaunches[upcomingLaunches.indexOf(uiModel)].copy(expanded = true)
                    val newUpcomingLaunches = upcomingLaunches.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _uiState.value = uiState.value.copy(upcomingLaunches = newUpcomingLaunches)
                }
                if (previousExpandedItem != null) {
                    val upcomingLaunches = uiState.value.allLaunches
                    val newUiModel =
                        upcomingLaunches[upcomingLaunches.indexOf(previousExpandedItem)].copy(
                            expanded = false
                        )
                    val newUpcomingLaunches = upcomingLaunches.toMutableList().apply {
                        replaceIf(newUiModel) {
                            newUiModel.id == it.id
                        }
                    }
                    _uiState.value = uiState.value.copy(upcomingLaunches = newUpcomingLaunches)
                }
            }
        }
    }

    fun onBookmarkClicked(uiModel: UiModel) {
        viewModelScope.launch(dispatcher) {
            if (uiModel.bookmarked) {
                bookmarksUseCase.deleteLaunch(uiModel.toDomainModel())
            } else {
                bookmarksUseCase.insertLaunch(uiModel.toDomainModel().copy(bookmarked = true))
            }
        }
    }
}

inline fun <T> MutableList<T>.replaceIf(toReplace: T, predicate: (T) -> Boolean): Boolean {
    var replaced = false
    for (i in 0 until size) {
        val currentValue = get(i)
        if (predicate(currentValue)) {
            removeAt(i)
            add(i, toReplace)
            replaced = true
            break
        }
    }
    return replaced
}