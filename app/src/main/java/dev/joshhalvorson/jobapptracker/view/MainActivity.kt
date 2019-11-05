package dev.joshhalvorson.jobapptracker.view

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.joshhalvorson.jobapptracker.R
import dev.joshhalvorson.jobapptracker.adapter.ApplicationsRecyclerviewAdapter
import dev.joshhalvorson.jobapptracker.component
import dev.joshhalvorson.jobapptracker.factory.MainActivityViewModelFactory
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.util.SwipeToDeleteCallback
import dev.joshhalvorson.jobapptracker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MainActivityViewModelFactory

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: ApplicationsRecyclerviewAdapter

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        context = this

        // Setting up adapter
        (applications_list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        val swipeHandler = object : SwipeToDeleteCallback(applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                MaterialAlertDialogBuilder(context)
                    .setTitle("Delete ${adapter.getApplication(viewHolder.adapterPosition).company}?")
                    .setMessage("This will remove the application from the database...")
                    .setPositiveButton(
                        "Delete"
                    ) { dialog, which ->
                        viewModel.removeApplication(
                            adapter.deleteApplication(
                                viewHolder.adapterPosition
                            ).company
                        )
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        adapter.notifyDataSetChanged()
                    }
                    .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(applications_list)
        adapter = ApplicationsRecyclerviewAdapter(object :
            ApplicationsRecyclerviewAdapter.OnItemClickListener {
            override fun onItemClicked(application: Application) {
                Log.i("onItemClicked", application.company)
                val dialog = AddApplicationDialogFragment.newInstance(application)
                dialog.onResult = {
                    Log.i("selectedRestaurant", it.moveAlong.toString())
                    viewModel.addApplication(it.company, it, update = true)
                    adapter.updateEntry(application, it)
                }
                dialog.show(supportFragmentManager, "dialog")
            }
        })
        applications_list.layoutManager = LinearLayoutManager(this)
        applications_list.adapter = adapter

        // Setting up viewmodel and populating viewmodel data with applications
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
            val dialog = AddApplicationDialogFragment()
            dialog.onResult = { application ->
                viewModel.addApplication(application.company, application)
            }
            dialog.show(supportFragmentManager, "add_application")
        }
    }
}
