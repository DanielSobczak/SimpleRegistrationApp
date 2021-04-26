package com.example.simpleregistrationapp.feature.registration

import com.airbnb.mvrx.MavericksState

data class RegistrationState(val name: String = "") : MavericksState
