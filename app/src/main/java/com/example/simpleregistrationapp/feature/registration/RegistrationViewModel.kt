package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksViewModel
import java.text.SimpleDateFormat
import java.util.*

class RegistrationViewModel(initialState: RegistrationState) :
    MavericksViewModel<RegistrationState>(initialState) {

    private val formatter = SimpleDateFormat("dd-MM-yyyy")

    fun updateName(name: String) = setState { copy(name = name) }
    fun updateEmail(email: String) = setState { copy(email = email) }
    fun updateDate(date: Date) =
        setState { copy(dateOfBirth = date, formattedDateOfBirth = formatter.format(date)) }
}
