package dev.joshhalvorson.jobapptracker.di.component

import dagger.Component
import dev.joshhalvorson.jobapptracker.di.module.AppModule
import dev.joshhalvorson.jobapptracker.di.module.RepositoryModule
import dev.joshhalvorson.jobapptracker.di.module.ViewModelModule
import dev.joshhalvorson.jobapptracker.view.activity.LoginActivity
import dev.joshhalvorson.jobapptracker.view.activity.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)

}