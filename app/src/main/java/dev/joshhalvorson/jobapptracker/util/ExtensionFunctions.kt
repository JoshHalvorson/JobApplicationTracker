package dev.joshhalvorson.jobapptracker.util

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/*
*  Adds a listener to the edit text that will clear any errors in its parent
*  input layout on text changed
* */
fun TextInputEditText.addRemoveErrorListener(layout: TextInputLayout) {
    val textInputEditText = this
    textInputEditText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            layout.isErrorEnabled = false
        }
    })
}