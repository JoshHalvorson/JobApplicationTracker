package dev.joshhalvorson.jobapptracker.network

import dev.joshhalvorson.jobapptracker.model.Application
import retrofit2.Call
import retrofit2.http.GET


interface ApplicationsApiInterface {

    @GET("/.json")
    fun getApplications(): Call<List<Application>>
}