package dev.joshhalvorson.jobapptracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepository

class MainActivityViewModel(private val applicationsRepository: ApplicationsRepository) :
    ViewModel() {

    //TODO("Cant re-add company with same details from one you've deleted in that session")

    private val _applications = MutableLiveData<ApplicationsResponse>()

    val applications: LiveData<ApplicationsResponse>
        get() = _applications

    fun getApplications(uid: String) {
        applicationsRepository.getApplications(
            uid,
            { applications -> _applications.value = applications },
            { t -> Log.e("MainActivity", "onFailure: ", t) }
        )
    }

    fun addApplication(uid: String, company: String, application: Application, update: Boolean = false) {
        applicationsRepository.addApplication(
            uid,
            company,
            application,
            { response -> Log.i("addApplication", response.toString()) },
            { t -> Log.e("addApplication", "onFailure: ", t) }
        )
        if (!update) {
            getApplications(uid)
        }
    }

    fun removeApplication(uid: String, company: String) {
        applicationsRepository.removeApplication(
            uid,
            company,
            { response -> Log.i("removeApplication", response.toString()) },
            { t -> Log.e("removeApplication", "onFailure: ", t) }
        )
    }

}