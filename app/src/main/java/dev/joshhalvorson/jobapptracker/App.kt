package dev.joshhalvorson.jobapptracker

import android.app.Application
import dev.joshhalvorson.jobapptracker.di.component.AppComponent
import dev.joshhalvorson.jobapptracker.di.component.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .build()
    }

}

lateinit var component: AppComponent