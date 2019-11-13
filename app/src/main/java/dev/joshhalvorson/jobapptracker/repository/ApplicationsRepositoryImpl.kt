package dev.joshhalvorson.jobapptracker.repository

import android.util.Log
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplicationsRepositoryImpl(private val api: ApplicationsApiInterface) :
    ApplicationsRepository {

    override fun getApplications(
        uid: String,
        onSuccess: (ApplicationsResponse) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        api.getApplications(uid).enqueue(object : Callback<ApplicationsResponse> {
            override fun onFailure(call: Call<ApplicationsResponse>, t: Throwable) {
                onFailure.invoke(t)
            }

            override fun onResponse(
                call: Call<ApplicationsResponse>,
                response: Response<ApplicationsResponse>
            ) {
                Log.i("applicationsResponse", response.body().toString())
                response.body()?.let { applications ->
                    onSuccess.invoke(applications)
                }
            }
        })
    }

    override fun addApplication(
        uid: String,
        company: String,
        application: Application,
        onSuccess: (ResponseBody) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        api.addApplication(uid, company, application).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onFailure.invoke(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("addApplicationsResponse", response.body().toString())
            }
        })
    }

    override fun removeApplication(
        uid: String,
        company: String,
        onSuccess: (Unit) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        api.removeApplication(uid, company).enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onFailure.invoke(t)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.i("removeAppResponse", response.body().toString())
            }

        })
    }


}