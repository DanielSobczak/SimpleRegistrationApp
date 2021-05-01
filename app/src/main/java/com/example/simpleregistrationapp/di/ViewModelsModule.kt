package com.example.simpleregistrationapp.di

import com.example.simpleregistrationapp.di.mavericks.AssistedViewModelFactory
import com.example.simpleregistrationapp.di.mavericks.MavericksViewModelComponent
import com.example.simpleregistrationapp.di.mavericks.ViewModelKey
import com.example.simpleregistrationapp.feature.confirmation.ConfirmationViewModel
import com.example.simpleregistrationapp.feature.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
internal interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun registrationVMFactory(factory: RegistrationViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmationViewModel::class)
    fun confirmationVMFactory(factory: ConfirmationViewModel.Factory): AssistedViewModelFactory<*, *>
}
