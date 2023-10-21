package ali.fathian.domain.repository

import ali.fathian.domain.common.Resource
import ali.fathian.domain.model.DomainLaunchModel
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {

    suspend fun getAllLaunches(): Resource<List<DomainLaunchModel>>

    suspend fun insertLaunch(launchModel: DomainLaunchModel)

    suspend fun deleteLaunch(launchModel: DomainLaunchModel)

    fun getLocalLaunches(): Flow<List<DomainLaunchModel>>

}