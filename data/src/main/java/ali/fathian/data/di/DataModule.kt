package ali.fathian.data.di

import ali.fathian.data.common.Constants.BASE_URL
import ali.fathian.data.local.LaunchDao
import ali.fathian.data.local.LaunchDatabase
import ali.fathian.data.remote.api.ApiService
import ali.fathian.data.repository.DefaultLaunchRepository
import ali.fathian.domain.repository.LaunchRepository
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLaunchRepository(apiService: ApiService, launchDao: LaunchDao): LaunchRepository {
        return DefaultLaunchRepository(apiService, launchDao)
    }

    @Singleton
    @Provides
    fun provideLaunchDatabase(
        @ApplicationContext context: Context
    ): LaunchDatabase {
        return Room.databaseBuilder(
            context,
            LaunchDatabase::class.java,
            "launchDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLaunchDao(launchDatabase: LaunchDatabase) = launchDatabase.launchDao()
}