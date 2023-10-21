package ali.fathian.data.repository

import ali.fathian.data.remote.api.ApiService
import ali.fathian.data.remote.dto.Launch
import ali.fathian.domain.DomainLaunchModel
import ali.fathian.domain.LaunchRepository
import ali.fathian.domain.common.Resource
import javax.inject.Inject

class DefaultLaunchRepository @Inject constructor(
    private val apiService: ApiService
) : LaunchRepository {

    override suspend fun getAllLaunches(): Resource<List<DomainLaunchModel>> {
        val response = apiService.getAllLaunches()
        return if (response.isSuccessful) {
            val launches = response.body()?.map { it.toDomainLaunchModel() }
            Resource.Success(launches ?: emptyList())
        } else {
            Resource.Error(message = response.message() ?: "Unknown Error")
        }
    }
}

fun Launch.toDomainLaunchModel(): DomainLaunchModel {
    return DomainLaunchModel(
        name = name,
        id = id
    )
}