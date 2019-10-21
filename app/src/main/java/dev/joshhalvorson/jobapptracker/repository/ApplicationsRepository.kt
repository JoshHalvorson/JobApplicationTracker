package dev.joshhalvorson.jobapptracker.repository

import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import okhttp3.ResponseBody

interface ApplicationsRepository  {

    fun getApplications(onSuccess: (ApplicationsResponse) -> Unit, onFailure: (t: Throwable) -> Unit)

    fun addApplication(application: Application, onSuccess: (ResponseBody) -> Unit, onFailure: (t: Throwable) -> Unit)

}