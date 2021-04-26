package com.example.simpleregistrationapp.feature.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

typealias StringCallback = (text: String) -> Unit

fun EditText.onChange(callback: StringCallback) {
    this.addTextChangedListener(AfterTextChangedWatcher(callback))
}


private class AfterTextChangedWatcher(private val callback: StringCallback) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        callback(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // NO-OP
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // NO-OP
    }
}
