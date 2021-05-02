package com.example.simpleregistrationapp.screens

import com.agoda.kakao.edit.KTextInputLayout
import com.agoda.kakao.picker.date.KDatePickerDialog
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KButton
import com.example.simpleregistrationapp.R

class RegistrationScreen : Screen<RegistrationScreen>() {
    val name = KTextInputLayout { withId(R.id.registration_input_name_container) }
    val email = KTextInputLayout { withId(R.id.registration_input_email_container) }
    val dateOfBirth = KTextInputLayout { withId(R.id.registration_input_date_container) }
    val calendar = KDatePickerDialog()
    val register = KButton { withId(R.id.registration_btn_register) }

}
