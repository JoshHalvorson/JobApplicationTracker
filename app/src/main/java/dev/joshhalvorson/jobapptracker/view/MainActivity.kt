package dev.joshhalvorson.jobapptracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import dev.joshhalvorson.jobapptracker.R
import dev.joshhalvorson.jobapptracker.adapter.ApplicationsRecyclerviewAdapter
import dev.joshhalvorson.jobapptracker.component
import dev.joshhalvorson.jobapptracker.factory.MainActivityViewModelFactory
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MainActivityViewModelFactory

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: ApplicationsRecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        adapter = ApplicationsRecyclerviewAdapter()
        applications_list.layoutManager = LinearLayoutManager(this)
        applications_list.adapter = adapter

        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)
        viewModel.applications.observe(this, Observer { response ->
            val apps = mutableListOf<Application>()
            response.entries.forEach {
                apps.add(it.value)
            }
            val sortedApps = apps.sortedWith(compareByDescending(Application::dateApplied))
            adapter.setData(sortedApps)
        })
        viewModel.getApplications()

        add_application_button.setOnClickListener {
            AddApplicationDialogFragment().show(supportFragmentManager, "add_application")
        }
    }
}
