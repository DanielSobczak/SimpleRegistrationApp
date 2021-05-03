package com.example.simpleregistrationapp.feature.confirmation

import com.airbnb.mvrx.MavericksState
import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.feature.utils.LoadingState

data class ConfirmationState(
    val user: User? = null,
    val loadingState: LoadingState = LoadingState.Loading
) : MavericksState
