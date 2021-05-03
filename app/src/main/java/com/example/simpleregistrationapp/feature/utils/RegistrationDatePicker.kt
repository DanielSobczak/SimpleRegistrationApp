package com.example.simpleregistrationapp.feature.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import org.threeten.bp.LocalDate

class RegistrationDatePicker : TextInputEditText {
    private var minDate: Long? = null
    private var maxDate: Long? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var registrationSelectedListener: ((LocalDate) -> Unit)? = null
    private var dialogDateSetListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val normalizedMonth = monthOfYear + 1 //DatePicker dialogs starts counting months from 0
            val date = LocalDate.of(year, normalizedMonth, dayOfMonth)
            registrationSelectedListener?.invoke(date)
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setMinDateRange(value: Long) {
        minDate = value
    }

    fun setMaxDateRange(value: Long) {
        maxDate = value
    }

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
        datePickerDialog?.apply {
            minDate?.let { datePicker.minDate = it }
            maxDate?.let { datePicker.maxDate = it }
            show()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        datePickerDialog?.dismiss()
    }
}
