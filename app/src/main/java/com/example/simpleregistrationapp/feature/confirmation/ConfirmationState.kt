package com.example.simpleregistrationapp.feature.confirmation

import com.airbnb.mvrx.MavericksState
import com.example.simpleregistrationapp.feature.utils.LoadingState

data class ConfirmationState(
    val dummy: String = "tralala",
    val user: User? = null,
    val loadingState: LoadingState = LoadingState.Loading
) : MavericksState
