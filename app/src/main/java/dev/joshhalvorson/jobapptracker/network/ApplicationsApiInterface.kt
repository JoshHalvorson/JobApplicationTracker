package dev.joshhalvorson.jobapptracker.network

import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApplicationsApiInterface {

    @GET("/.json")
    fun getApplications(): Call<ApplicationsResponse>

    @POST("/.json")
    fun addApplication(@Body application: Application): Call<ResponseBody>

}