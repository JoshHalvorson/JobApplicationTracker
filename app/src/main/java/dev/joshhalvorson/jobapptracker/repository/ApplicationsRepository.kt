package dev.joshhalvorson.jobapptracker.repository

import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import okhttp3.ResponseBody

interface ApplicationsRepository {

    fun getApplications(
        uid: String,
        onSuccess: (ApplicationsResponse) -> Unit,
        onFailure: (t: Throwable) -> Unit
    )

    fun addApplication(
        uid: String,
        company: String,
        application: Application,
        onSuccess: (ResponseBody) -> Unit,
        onFailure: (t: Throwable) -> Unit
    )

    fun removeApplication(
        uid: String,
        company: String,
        onSuccess: (Unit) -> Unit,
        onFailure: (t: Throwable) -> Unit
    )
}