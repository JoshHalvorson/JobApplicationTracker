package dev.joshhalvorson.jobapptracker.repository

import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse

interface ApplicationsRepository  {

    fun getApplications(onSuccess: (ApplicationsResponse) -> Unit, onFailure: (t: Throwable) -> Unit)

}