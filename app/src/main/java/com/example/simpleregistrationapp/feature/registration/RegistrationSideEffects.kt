package com.example.simpleregistrationapp.feature.registration

sealed class RegistrationSideEffects {
    object OpenConfirmationScreen : RegistrationSideEffects()
}
