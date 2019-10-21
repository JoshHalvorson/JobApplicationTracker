package dev.joshhalvorson.jobapptracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepositoryImpl
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepository

class MainActivityViewModel(private val applicationsRepository: ApplicationsRepository) :
    ViewModel() {

    private val _applications = MutableLiveData<ApplicationsResponse>()

    val applications: LiveData<ApplicationsResponse>
        get() = _applications

    fun getApplications() {
        applicationsRepository.getApplications(
            { applications -> _applications.value = applications },
            { t -> Log.e("MainActivity", "onFailure: ", t) }
        )
    }

    fun addApplication(application: Application) {
        applicationsRepository.addApplication(
            application,
            { response -> Log.i("addApplication", response.toString()) },
            { t -> Log.e("MainActivity", "onFailure: ", t) }
        )
    }

}