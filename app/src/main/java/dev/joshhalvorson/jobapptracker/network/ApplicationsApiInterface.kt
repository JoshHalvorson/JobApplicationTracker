package dev.joshhalvorson.jobapptracker.network

import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApplicationsApiInterface {

    @GET("/.json")
    fun getApplications(): Call<ApplicationsResponse>

    @PUT("{company}/.json")
    fun addApplication(@Path("company") company: String, @Body application: Application): Call<ResponseBody>

    @DELETE("{company}/.json")
    fun removeApplication(@Path("company") company: String): Call<Unit>

}