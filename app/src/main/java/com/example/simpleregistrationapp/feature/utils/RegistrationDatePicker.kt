package com.example.simpleregistrationapp.feature.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class RegistrationDatePicker : TextInputEditText {

    private val myCalendar: Calendar = Calendar.getInstance()
    private var datePickerDialog: DatePickerDialog? = null
    private var dateSetListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dateSelectedListener?.invoke(myCalendar.time)
        }
    private var dateSelectedListener: ((Date) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    public fun setOnDatePickedListener(dateSelectedListener: (Date) -> Unit) {
        this.dateSelectedListener = dateSelectedListener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.setOnClickListener {
            openDialogPicker()
        }
    }

    private fun openDialogPicker() {
        datePickerDialog?.dismiss()
        datePickerDialog = DatePickerDialog(
            this.context, dateSetListener, myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog?.show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        datePickerDialog?.dismiss()
    }
}
