package ali.fathian.domain.di

import ali.fathian.domain.DefaultGetAllLaunchesUseCase
import ali.fathian.domain.GetAllLaunchesUseCase
import ali.fathian.domain.LaunchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetAllLaunchesUseCase(repository: LaunchRepository): GetAllLaunchesUseCase {
        return DefaultGetAllLaunchesUseCase(repository)
    }
}