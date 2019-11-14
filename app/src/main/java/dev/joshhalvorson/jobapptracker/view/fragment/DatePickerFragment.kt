package dev.joshhalvorson.jobapptracker.view.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    var onResult: ((date: String) -> Unit)? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user

        // get the actual month number, not the indexed month number that returns
        val actualMonth = month + 1
        var stringMonth = actualMonth.toString()
        var stringDate = day.toString()
        Log.i("datePicker", "$actualMonth/$day/$year")
        if (actualMonth < 10) {
            stringMonth = "0$stringMonth"
        }
        if (day < 10) {
            stringDate = "0$stringDate"
        }
        onResult?.invoke("$stringMonth/$stringDate/$year")
    }
}
