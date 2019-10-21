package dev.joshhalvorson.jobapptracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.joshhalvorson.jobapptracker.factory.MainActivityViewModelFactory
import dev.joshhalvorson.jobapptracker.network.ApplicationsApiInterface
import dev.joshhalvorson.jobapptracker.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MainActivityViewModelFactory

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        viewModel.applications.observe(this, Observer {
            it.entries.forEach {
                Log.i("dwadadwdw", it.value.toString())
            }
        })

        viewModel.getApplications()
    }
}
