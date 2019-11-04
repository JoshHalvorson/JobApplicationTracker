package dev.joshhalvorson.jobapptracker.repository

import android.util.Log
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.model.ApplicationsResponse
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplicationsRepositoryImpl(private val api: ApplicationsApiInterface): ApplicationsRepository {

    override fun getApplications(onSuccess: (ApplicationsResponse) -> Unit, onFailure: (t: Throwable) -> Unit) {
        api.getApplications().enqueue(object: Callback<ApplicationsResponse> {
            override fun onFailure(call: Call<ApplicationsResponse>, t: Throwable) {
                onFailure.invoke(t)
            }

            override fun onResponse(call: Call<ApplicationsResponse>, response: Response<ApplicationsResponse>) {
                Log.i("applicationsResponse", response.body().toString())
                response.body()?.let { applications ->
                    onSuccess.invoke(applications)
                }
            }
        })
    }

    override fun addApplication(
        company: String,
        application: Application,
        onSuccess: (ResponseBody) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        api.addApplication(company, application).enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onFailure.invoke(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i("addApplicationsResponse", response.body().toString())
            }
        })
    }

}