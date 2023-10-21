package ali.fathian.domain.use_cases

import ali.fathian.domain.model.DomainLaunchModel
import ali.fathian.domain.repository.LaunchRepository
import javax.inject.Inject

class BookmarksUseCase @Inject constructor(
    private val repository: LaunchRepository
) {

    suspend fun insertLaunch(launchModel: DomainLaunchModel) {
        repository.insertLaunch(launchModel)
    }

    suspend fun deleteLaunch(launchModel: DomainLaunchModel) {
        repository.deleteLaunch(launchModel)
    }

    fun getLocalLaunches() = repository.getLocalLaunches()
}