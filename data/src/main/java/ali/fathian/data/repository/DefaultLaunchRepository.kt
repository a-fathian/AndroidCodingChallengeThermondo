package ali.fathian.data.repository

import ali.fathian.data.remote.api.ApiService
import ali.fathian.data.remote.dto.mapper.toDomainLaunchModel
import ali.fathian.domain.common.Resource
import ali.fathian.domain.model.DomainLaunchModel
import ali.fathian.domain.repository.LaunchRepository
import javax.inject.Inject

class DefaultLaunchRepository @Inject constructor(
    private val apiService: ApiService
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
}

