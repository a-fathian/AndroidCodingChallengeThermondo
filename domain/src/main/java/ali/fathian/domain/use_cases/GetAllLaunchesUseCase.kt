package ali.fathian.domain.use_cases

import ali.fathian.domain.repository.LaunchRepository
import javax.inject.Inject

class GetAllLaunchesUseCase @Inject constructor(
    private val repository: LaunchRepository
) {

    suspend operator fun invoke() = repository.getAllLaunches()
}
