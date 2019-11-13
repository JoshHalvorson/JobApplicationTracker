package dev.joshhalvorson.jobapptracker.network

import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApplicationsApiInterface {

    @GET("{uid}/.json")
    fun getApplications(
        @Path("uid") uid: String
    ): Call<ApplicationsResponse>

    @PUT("{uid}/{company}/.json")
    fun addApplication(
        @Path("uid") uid: String,
        @Path("company") company: String,
        @Body application: Application
    ): Call<ResponseBody>

    @DELETE("{uid}/{company}/.json")
    fun removeApplication(
        @Path("uid") uid: String,
        @Path("company") company: String
    ): Call<Unit>

}