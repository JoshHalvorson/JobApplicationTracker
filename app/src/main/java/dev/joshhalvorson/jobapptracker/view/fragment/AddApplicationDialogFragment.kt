package dev.joshhalvorson.jobapptracker.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import dev.joshhalvorson.jobapptracker.R
import dev.joshhalvorson.jobapptracker.model.Application
import dev.joshhalvorson.jobapptracker.util.AddApplicationFormValidator
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
        setCheckboxVisibilityListeners()

        application?.let {
            company_name_edit_text.setText(it.company)
            add_application_dialog_title.text = "Update an applicaton"
            company_name_input_layout.isEnabled = false
            date_applied_edit_text.setText(it.dateApplied)
            replied_check_box.isChecked = it.response
            first_interview_checkbox.isChecked = it.firstInterview
            second_interview_checkbox.isChecked = it.secondInterview
            third_interview_checkbox.isChecked = it.thirdInterview
            offer_checkbox.isChecked = it.offer
            add_application_button.text = "Update"
        }

        add_application_button.setOnClickListener {
            if (validateAddApplicationFormFields(
                    companyName = company_name_input_layout.editText?.text.toString(),
                    dateApplied = date_applied_input_layout.editText?.text.toString()
                )
            ) {
                val application = Application(
                    company = company_name_input_layout.editText?.text.toString(),
                    dateApplied = date_applied_input_layout.editText?.text.toString(),
                    response = replied_check_box.isChecked,
                    firstInterview = first_interview_checkbox.isChecked,
                    secondInterview = second_interview_checkbox.isChecked,
                    thirdInterview = third_interview_checkbox.isChecked,
                    offer = offer_checkbox.isChecked
                )
                onResult?.invoke(application)
                dismiss()
            }
        }

        date_applied_edit_text.setOnClickListener {
            showDatePickerDialog()
        }
    }

     private fun validateAddApplicationFormFields(companyName: String, dateApplied: String): Boolean {
         val validatorReturn = AddApplicationFormValidator.validateAddApplicationFormFields(companyName, dateApplied)
         when {
             validatorReturn == null -> {
                 // both correct
                 return true
             }
             validatorReturn.size > 1 -> {
                 // both wrong
                 company_name_input_layout.isErrorEnabled = true
                 date_applied_input_layout.isErrorEnabled = true
                 company_name_input_layout.error = validatorReturn[0]
                 date_applied_input_layout.error = validatorReturn[1]
                 return false
             }
             validatorReturn[0] == AddApplicationFormValidator.COMPANY_NAME_WRONG_FLAG -> {
                 // company name wrong
                 company_name_input_layout.isErrorEnabled = true
                 company_name_input_layout.error = validatorReturn[0]
                 return false
             }
             else -> {
                 // date applied wrong
                 date_applied_input_layout.isErrorEnabled = true
                 date_applied_input_layout.error = validatorReturn[0]
                 return false
             }
         }
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

    private fun setCheckboxVisibilityListeners() {
        replied_check_box.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                first_interview_checkbox.visibility = View.VISIBLE
                offer_checkbox.visibility = View.VISIBLE
            } else {
                first_interview_checkbox.visibility = View.GONE
                second_interview_checkbox.visibility = View.GONE
                third_interview_checkbox.visibility = View.GONE
                offer_checkbox.visibility = View.GONE
                first_interview_checkbox.isChecked = false
                second_interview_checkbox.isChecked = false
                third_interview_checkbox.isChecked = false
                offer_checkbox.isChecked = false
            }
        }

        first_interview_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                second_interview_checkbox.visibility = View.VISIBLE
            } else {
                second_interview_checkbox.visibility = View.GONE
                third_interview_checkbox.visibility = View.GONE
                second_interview_checkbox.isChecked = false
                third_interview_checkbox.isChecked = false
            }
        }

        second_interview_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                third_interview_checkbox.visibility = View.VISIBLE
            } else {
                third_interview_checkbox.visibility = View.GONE
                third_interview_checkbox.isChecked = false
            }
        }
    }
}
