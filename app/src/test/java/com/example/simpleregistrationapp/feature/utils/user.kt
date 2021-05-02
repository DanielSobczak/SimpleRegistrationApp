package com.example.simpleregistrationapp.feature.utils

import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.feature.registration.RegistrationRequest
import org.threeten.bp.LocalDate

val dummyUser = User("Foo", "foo@bar.com", LocalDate.now())
val validRegistrationRequest = RegistrationRequest("Foo", "foo@bar.com", LocalDate.now())
