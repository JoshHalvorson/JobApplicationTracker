package dev.joshhalvorson.jobapptracker.di.module

import dagger.Module
import dagger.Provides
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://job-app-tracker-34d9f.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApplicationsApiInterface {
        return retrofit.create(ApplicationsApiInterface::class.java)
    }

}