package dev.joshhalvorson.jobapptracker.di.module

import dagger.Module
import dagger.Provides
import dev.joshhalvorson.jobapptracker.factory.MainActivityViewModelFactory
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepository

@Module
class ViewModelModule {

    @Provides
    fun providesMainActivityViewModelFactory(applicationsRepository: ApplicationsRepository): MainActivityViewModelFactory {
        return MainActivityViewModelFactory(applicationsRepository)
    }

}