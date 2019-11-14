package dev.joshhalvorson.jobapptracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepository

class MainActivityViewModel(private val applicationsRepository: ApplicationsRepository) :
    ViewModel() {

    private val _applications = MutableLiveData<List<Application>>()

    val applications: LiveData<List<Application>>
        get() = _applications

    fun getApplications(uid: String) {
        val tempApplicationsList: ArrayList<Application> = arrayListOf()
        applicationsRepository.getApplications(
            uid,
            { applications ->
                applications.forEach {
                    Log.i("allApps", "Key: ${it.key}, Application: ${it.value}")
                    tempApplicationsList.add(it.value)
                }
                _applications.value = tempApplicationsList
            },
            { t -> Log.e("MainActivity", "onFailure: ", t) }
        )
    }

    fun addApplication(
        uid: String,
        company: String,
        application: Application
    ) {
        applicationsRepository.addApplication(
            uid,
            company,
            application,
            { response -> Log.i("addApplication", response.toString()) },
            { t -> Log.e("addApplication", "onFailure: ", t) }
        )
        addApplicationToData(application)
    }

    fun updateApplication(
        uid: String,
        oldApplication: Application,
        newApplication: Application
    ) {
        applicationsRepository.addApplication(
            uid,
            newApplication.company,
            newApplication,
            { response -> Log.i("addApplication", response.toString()) },
            { t -> Log.e("addApplication", "onFailure: ", t) }
        )
        updateApplicationInData(oldApplication, newApplication)
    }

    fun removeApplication(uid: String, application: Application) {
        applicationsRepository.removeApplication(
            uid,
            application.company,
            { response -> Log.i("removeApplication", response.toString()) },
            { t -> Log.e("removeApplication", "onFailure: ", t) }
        )
        removeApplicationFromData(application)
    }

    private fun addApplicationToData(application: Application) {
        val tempApplicationsList = _applications.value?.toMutableList()
        tempApplicationsList?.add(application)
        _applications.value = tempApplicationsList
    }

    private fun removeApplicationFromData(application: Application) {
        val tempApplicationsList = _applications.value?.toMutableList()
        tempApplicationsList?.remove(application)
        _applications.value = tempApplicationsList
    }

    private fun updateApplicationInData(oldApplication: Application, newApplication: Application) {
        removeApplicationFromData(oldApplication)
        addApplicationToData(newApplication)
    }

}