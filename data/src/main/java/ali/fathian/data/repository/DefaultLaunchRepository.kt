package ali.fathian.data.repository

import ali.fathian.data.local.LaunchDao
import ali.fathian.data.remote.api.ApiService
import ali.fathian.data.remote.dto.mapper.toDomainLaunchModel
import ali.fathian.data.remote.dto.mapper.toDomainModel
import ali.fathian.data.remote.dto.mapper.toLaunchEntity
import ali.fathian.domain.common.Resource
import ali.fathian.domain.model.DomainLaunchModel
import ali.fathian.domain.repository.LaunchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultLaunchRepository @Inject constructor(
    private val apiService: ApiService,
    private val launchDao: LaunchDao
) : LaunchRepository {

    override suspend fun getAllLaunches(): Resource<List<DomainLaunchModel>> {
        return try {
            val response = apiService.getAllLaunches()
            if (response.isSuccessful) {
                val launches = response.body()?.map { it.toDomainLaunchModel() }
                Resource.Success(launches ?: emptyList())
            } else {
                Resource.Error(message = response.message() ?: "Unknown Error")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Check your internet connection")
        }
    }

    override suspend fun insertLaunch(launchModel: DomainLaunchModel) {
        launchDao.insertLaunch(launchModel.toLaunchEntity())
    }

    override suspend fun deleteLaunch(launchModel: DomainLaunchModel) {
        launchDao.deleteLaunch(launchModel.toLaunchEntity())
    }

    override fun getLocalLaunches(): Flow<List<DomainLaunchModel>> {
        return launchDao.getAllLaunches().map { it.map { item -> item.toDomainModel() } }
    }
}

