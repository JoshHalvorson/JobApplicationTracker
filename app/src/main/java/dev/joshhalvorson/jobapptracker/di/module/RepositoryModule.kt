package dev.joshhalvorson.jobapptracker.di.module

import dagger.Module
import dagger.Provides
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepository
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepositoryImpl
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesApplicationsRepository(api: ApplicationsApiInterface) : ApplicationsRepository {
        return ApplicationsRepositoryImpl(api)
    }

}