package com.example.simpleregistrationapp.feature.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import org.threeten.bp.LocalDate

class RegistrationDatePicker : TextInputEditText {
    private var datePickerDialog: DatePickerDialog? = null
    private var registrationSelectedListener: ((LocalDate) -> Unit)? = null
    private var dialogDateSetListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val date = LocalDate.of(year, monthOfYear, dayOfMonth)
            registrationSelectedListener?.invoke(date)
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setOnDatePickedListener(dateSelectedListener: (LocalDate) -> Unit) {
        this.registrationSelectedListener = dateSelectedListener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.setOnClickListener {
            openDialogPicker()
        }
    }

    private fun openDialogPicker() {
        val now = LocalDate.now()
        datePickerDialog?.dismiss()
        datePickerDialog = DatePickerDialog(
            this.context,
            dialogDateSetListener,
            now.year,
            now.monthValue,
            now.dayOfMonth
        )
        datePickerDialog?.show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        datePickerDialog?.dismiss()
    }
}
