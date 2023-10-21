package ali.fathian.presentation.launch

import ali.fathian.domain.GetAllLaunchesUseCase
import ali.fathian.domain.common.Resource
import ali.fathian.presentation.common.DispatcherIO
import ali.fathian.presentation.model.UiModel
import ali.fathian.presentation.model.mapper.toUiModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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

    private val _uiState = mutableStateListOf<UiModel>()
    val uiState: SnapshotStateList<UiModel> = _uiState

//    private val loading

    init {
        viewModelScope.launch(dispatcher) {
            val launches = launchUseCase()
            if (launches is Resource.Success) {
                _uiState.addAll(launches.data?.map { it.toUiModel() } ?: emptyList())
            }
        }
    }
}