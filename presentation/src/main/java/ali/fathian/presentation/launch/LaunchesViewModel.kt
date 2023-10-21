package ali.fathian.presentation.launch

import ali.fathian.domain.common.Resource
import ali.fathian.domain.use_cases.GetAllLaunchesUseCase
import ali.fathian.presentation.common.DispatcherIO
import ali.fathian.presentation.model.Launches
import ali.fathian.presentation.model.mapper.toUiModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchUseCase: GetAllLaunchesUseCase,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = mutableStateOf(Launches())
    val uiState: State<Launches> = _uiState

    fun fetchLaunches() {
        viewModelScope.launch(dispatcher) {
            _uiState.value = uiState.value.copy(loading = true)
            val launches = launchUseCase()
            if (launches is Resource.Success) {
                launches.data?.let {
                    _uiState.value =
                        Launches(
                            allLaunches = it.map { item -> item.toUiModel() },
                            upcomingLaunches = it.filter { item ->
                                item.upcoming
                            }.map { item -> item.toUiModel() },
                            pastLaunches = it.filter { item ->
                                !item.upcoming
                            }.map { item -> item.toUiModel() },
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
}