package dev.joshhalvorson.jobapptracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.joshhalvorson.jobapptracker.R
import dev.joshhalvorson.jobapptracker.model.Application
import kotlinx.android.synthetic.main.applications_list_item.view.*

class ApplicationsRecyclerviewAdapter() : RecyclerView.Adapter<ApplicationsRecyclerviewAdapter.ViewHolder>() {
    private var data: ArrayList<Application> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.applications_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount() = data.size

    fun setData(applications: List<Application>) {
        data.clear()
        data.addAll(applications)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val companyName = itemView.applications_list_item_company_name
        private val dateApplied = itemView.applications_list_item_date_applied
        private val reply = itemView.applications_list_item_reply
        private val movingForward = itemView.applications_list_item_moving_forward

        fun bindItem(item: Application) {
            companyName.text = item.company
            dateApplied.text = "Date applied: ${item.dateApplied}"

            if (item.response) {
                reply.text = "Replied"
            } else{
                reply.text = "Haven't replied"
            }

            if (item.moveAlong) {
                movingForward.text = "Moving forward"
            } else {
                movingForward.text = "Not moving forward"
            }
        }
    }

}