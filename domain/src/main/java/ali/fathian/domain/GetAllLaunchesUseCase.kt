package ali.fathian.domain

import javax.inject.Inject

class GetAllLaunchesUseCase @Inject constructor(
    private val repository: LaunchRepository
) {

    suspend operator fun invoke() = repository.getAllLaunches()
}
