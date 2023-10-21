package ali.fathian.data.remote.api

import ali.fathian.data.remote.dto.Launch
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v5/launches")
    suspend fun getAllLaunches(): Response<List<Launch>>

//    @GET("v4/ships")
//    suspend fun getAllShips(): List<ShipsDto>
//
//    @GET("v4/capsules")
//    suspend fun getAllCapsules(): List<CapsulesDto>
//
//    @GET("v4/rockets/id")
//    suspend fun getAllRockets(): List<RocketsDto>
//
//    @GET("v4/company")
//    suspend fun getCompanyInfo(): CompanyInfoDto
//
//    @GET("v4/history")
//    suspend fun getCompanyHistory(): List<HistoryDto>

}