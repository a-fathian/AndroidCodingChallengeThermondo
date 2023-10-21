package ali.fathian.domain

import ali.fathian.domain.common.Resource

interface LaunchRepository {

    suspend fun getAllLaunches(): Resource<List<DomainLaunchModel>>

}