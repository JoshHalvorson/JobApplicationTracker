package dev.joshhalvorson.jobapptracker.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.repository.ApplicationsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivityViewModel(private val applicationsRepository: ApplicationsRepository) :
    ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val dateTimeStrToLocalDateTime: (Application) -> LocalDate = {
        LocalDate.parse(it.dateApplied, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
    }

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
                val sortedApps = tempApplicationsList.sortedByDescending(dateTimeStrToLocalDateTime)
                _applications.value = sortedApps
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
        val sortedApps = tempApplicationsList?.sortedByDescending(dateTimeStrToLocalDateTime)
        _applications.value = sortedApps
    }

    private fun removeApplicationFromData(application: Application) {
        val tempApplicationsList = _applications.value?.toMutableList()
        tempApplicationsList?.remove(application)
        val sortedApps = tempApplicationsList?.sortedByDescending(dateTimeStrToLocalDateTime)
        _applications.value = sortedApps
    }

    private fun updateApplicationInData(oldApplication: Application, newApplication: Application) {
        val tempApplicationsList = _applications.value?.toMutableList()
        tempApplicationsList?.set(tempApplicationsList.indexOf(oldApplication), newApplication)
        val sortedApps = tempApplicationsList?.sortedByDescending(dateTimeStrToLocalDateTime)
        _applications.value = sortedApps
    }

}