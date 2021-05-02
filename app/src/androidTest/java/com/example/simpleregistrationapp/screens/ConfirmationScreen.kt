package com.example.simpleregistrationapp.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.simpleregistrationapp.R

class ConfirmationScreen : Screen<ConfirmationScreen>() {
    val name = KTextView { withId(R.id.confirmation_user_name) }
    val email = KTextView { withId(R.id.confirmation_user_mail) }
    val dateOfBirth = KTextView { withId(R.id.confirmation_user_date_of_birth) }
}
