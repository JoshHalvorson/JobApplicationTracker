package dev.joshhalvorson.jobapptracker.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import dev.joshhalvorson.jobapptracker.R
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.util.addRemoveErrorListener
import kotlinx.android.synthetic.main.fragment_add_application_dialog.*

private const val APPLICATION = "application"

class AddApplicationDialogFragment : DialogFragment() {
    var onResult: ((application: Application) -> Unit)? = null
    private var application: Application? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            application = it.getSerializable(APPLICATION) as Application
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_application_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        company_name_edit_text.addRemoveErrorListener(company_name_input_layout)
        date_applied_edit_text.addRemoveErrorListener(date_applied_input_layout)

        application?.let {
            company_name_edit_text.setText(it.company)
            company_name_input_layout.isEnabled = false
            date_applied_edit_text.setText(it.dateApplied)
            replied_check_box.isChecked = it.response
            move_along_check_box.isChecked = it.moveAlong
            add_application_button.text = "Update"
        }

        add_application_button.setOnClickListener {
            if (validateAddApplicationFormFields()) {
                val application = Application(
                    company = company_name_input_layout.editText?.text.toString(),
                    dateApplied = date_applied_input_layout.editText?.text.toString(),
                    response = replied_check_box.isChecked,
                    moveAlong = move_along_check_box.isChecked
                )
                onResult?.invoke(application)
                dismiss()
            }
        }

        date_applied_edit_text.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun validateAddApplicationFormFields(): Boolean {
        var invalid = true
        if (TextUtils.isEmpty(company_name_input_layout.editText?.text.toString())) {
            company_name_input_layout.isErrorEnabled = true
            company_name_input_layout.error = "Enter a company name"
            invalid = false
        }
        if (TextUtils.isEmpty(date_applied_input_layout.editText?.text.toString())) {
            date_applied_input_layout.isErrorEnabled = true
            date_applied_input_layout.error = "Enter date applied"
            invalid = false
        }
        return invalid
    }

    companion object {
        @JvmStatic
        fun newInstance(application: Application) =
            AddApplicationDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(APPLICATION, application)
                }
            }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.onResult = { date ->
            updateDateEditText(date)
        }
        fragmentManager?.let { newFragment.show(it, "datePicker") }
    }

    private fun updateDateEditText(date: String) {
        date_applied_edit_text.setText(date)
    }
}
