package ali.fathian.domain

import ali.fathian.domain.common.Resource
import javax.inject.Inject

class DefaultGetAllLaunchesUseCase @Inject constructor(
    private val repository: LaunchRepository
) : GetAllLaunchesUseCase {

    override suspend operator fun invoke() = repository.getAllLaunches()
}

interface GetAllLaunchesUseCase {

    suspend operator fun invoke(): Resource<List<DomainLaunchModel>>
}