package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksViewModel

class RegistrationViewModel(initialState: RegistrationState) :
    MavericksViewModel<RegistrationState>(initialState) {

    fun updateName(name: String) = setState { copy(name = name) }
}
