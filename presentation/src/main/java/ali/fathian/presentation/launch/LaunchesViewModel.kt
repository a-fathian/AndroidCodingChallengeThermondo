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
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchUseCase: GetAllLaunchesUseCase,
    private val bookmarksUseCase: BookmarksUseCase,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = mutableStateOf(Launches())
    val uiState: State<Launches> = _uiState

    private val _bookmarks = MutableStateFlow<List<UiModel>?>(null)
    val bookmarks = _bookmarks.asStateFlow()

    init {
        fetchBookmarks()
    }

    private fun fetchBookmarks() {
        viewModelScope.launch(dispatcher) {
            bookmarksUseCase.getLocalLaunches().collect {
                Log.d("fetchBookmarks", "fetchBookmarks: ${it.size}")
                _bookmarks.emit(it.map { item -> item.toUiModel() })
            }
        }
    }

    fun fetchLaunches() {
        viewModelScope.launch(dispatcher) {
            _uiState.value = uiState.value.copy(loading = true)
            val launches = launchUseCase()
            if (launches is Resource.Success) {
                launches.data?.let {
                    _uiState.value =
                        Launches(
                            allLaunches = it.map { item -> item.toUiModel() },
                            upcomingLaunches = it.map { item -> item.toUiModel() }
                                .filter { item -> item.upcoming },
                            pastLaunches = it.map { item -> item.toUiModel() }
                                .filter { item -> !item.upcoming },
                            errorMessage = "",
                            loading = false
                        )
                }
            } else {
                _uiState.value =
                    Launches(errorMessage = launches.message ?: "Unknown Error", loading = false)
            }
        }
    }

    fun onItemClick(uiModel: UiModel, origin: Origin) {
        when (origin) {
            Origin.AllLaunches -> {
                val previousExpandedItem = _uiState.value.allLaunches.find { it.expanded }
                if (uiModel != previousExpandedItem) {
                    val allLaunches = uiState.value.allLaunches
                    allLaunches[allLaunches.indexOf(uiModel)].expanded = true
                    _uiState.value = uiState.value.copy(allLaunches = allLaunches)
                }
                if (previousExpandedItem != null) {
                    val allLaunches = uiState.value.allLaunches
                    allLaunches[allLaunches.indexOf(previousExpandedItem)].expanded = false
                    _uiState.value = uiState.value.copy(allLaunches = allLaunches)
                }
            }

            Origin.BookmarkLaunches -> {
            }

            Origin.PastLaunches -> {
                val previousExpandedItem = _uiState.value.pastLaunches.find { it.expanded }
                if (uiModel != previousExpandedItem) {
                    val pastLaunches = uiState.value.pastLaunches
                    pastLaunches[pastLaunches.indexOf(uiModel)].expanded = true
                    _uiState.value = uiState.value.copy(pastLaunches = pastLaunches)
                }
                if (previousExpandedItem != null) {
                    val pastLaunches = uiState.value.pastLaunches
                    pastLaunches[pastLaunches.indexOf(previousExpandedItem)].expanded = false
                    _uiState.value = uiState.value.copy(pastLaunches = pastLaunches)
                }
            }

            Origin.UpcomingLaunches -> {
                val previousExpandedItem = _uiState.value.upcomingLaunches.find { it.expanded }
                if (uiModel != previousExpandedItem) {
                    val upcomingLaunches = uiState.value.upcomingLaunches
                    upcomingLaunches[upcomingLaunches.indexOf(uiModel)].expanded = true
                    _uiState.value = uiState.value.copy(upcomingLaunches = upcomingLaunches)
                }
                if (previousExpandedItem != null) {
                    val upcomingLaunches = uiState.value.upcomingLaunches
                    upcomingLaunches[upcomingLaunches.indexOf(previousExpandedItem)].expanded =
                        false
                    _uiState.value = uiState.value.copy(upcomingLaunches = upcomingLaunches)
                }
            }
        }
    }

    fun onBookmarkClicked(uiModel: UiModel) {
        viewModelScope.launch(dispatcher) {
            if (uiModel.bookmarked) {
                bookmarksUseCase.deleteLaunch(uiModel.toDomainModel())
            } else {
                bookmarksUseCase.insertLaunch(uiModel.toDomainModel())
            }
        }
    }
}