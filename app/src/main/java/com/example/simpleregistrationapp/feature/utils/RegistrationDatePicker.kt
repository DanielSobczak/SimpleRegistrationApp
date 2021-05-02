package com.example.simpleregistrationapp.feature.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import org.threeten.bp.LocalDate

class RegistrationDatePicker : TextInputEditText {

    private var datePickerDialog: DatePickerDialog? = null
    private var dateSetListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val date = LocalDate.of(year, monthOfYear, dayOfMonth)
            dateSelectedListener?.invoke(date)
        }
    private var dateSelectedListener: ((LocalDate) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setOnDatePickedListener(dateSelectedListener: (LocalDate) -> Unit) {
        this.dateSelectedListener = dateSelectedListener
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
            dateSetListener,
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
