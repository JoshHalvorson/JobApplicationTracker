package dev.joshhalvorson.jobapptracker.network

import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import retrofit2.Call
import retrofit2.http.GET


interface ApplicationsApiInterface {

    @GET("/.json")
    fun getApplications(): Call<ApplicationsResponse>
}