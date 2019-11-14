package dev.joshhalvorson.jobapptracker.view.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import dev.joshhalvorson.jobapptracker.R
import dev.joshhalvorson.jobapptracker.adapter.ApplicationsRecyclerviewAdapter
import dev.joshhalvorson.jobapptracker.component
import dev.joshhalvorson.jobapptracker.factory.MainActivityViewModelFactory
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.util.SwipeToDeleteCallback
import dev.joshhalvorson.jobapptracker.view.fragment.AddApplicationDialogFragment
import dev.joshhalvorson.jobapptracker.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MainActivityViewModelFactory

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: ApplicationsRecyclerviewAdapter

    private lateinit var context: Context

    private var applied = 0
    private var replied = 0
    private var movedAlong = 0

    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        context = this

        FirebaseAuth.getInstance().currentUser?.let { user ->
            uid = user.uid
        }

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
                            uid,
                            adapter.getApplication(viewHolder.adapterPosition)
                        )
                        countApplications(adapter.getApplications())
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
            override fun onItemClicked(index: Int, application: Application) {
                Log.i("onItemClicked", application.company)
                val dialog =
                    AddApplicationDialogFragment.newInstance(
                        application
                    )
                dialog.onResult = { app ->
                    Log.i("selectedRestaurant", app.moveAlong.toString())
                    viewModel.updateApplication(
                        uid,
                        oldApplication = application,
                        newApplication = app
                    )
                    countApplications(adapter.getApplications())
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
            response.forEach {
                apps.add(it)
            }
            countApplications(apps)
            adapter.setData(apps)
        })
        viewModel.getApplications(uid)

        add_application_button.setOnClickListener {
            val dialog =
                AddApplicationDialogFragment()
            dialog.onResult = { application ->
                viewModel.addApplication(uid, application.company, application)
            }
            dialog.show(supportFragmentManager, "add_application")
        }
    }

    private fun countApplications(applications: List<Application>) {
        applied = 0
        replied = 0
        movedAlong = 0
        applications.forEach { application ->
            applied += 1
            if (application.response) {
                replied += 1
            }
            if (application.moveAlong) {
                movedAlong += 1
            }
        }
        applied_text.text = "Applied: $applied"
        replied_text.text = "Replied: $replied"
        move_along_text.text = "Moved along: $movedAlong"
    }
}
