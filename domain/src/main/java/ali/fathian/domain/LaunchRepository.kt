package ali.fathian.domain

import ali.fathian.domain.common.Resource
import ali.fathian.domain.model.DomainLaunchModel

interface LaunchRepository {

    suspend fun getAllLaunches(): Resource<List<DomainLaunchModel>>

}