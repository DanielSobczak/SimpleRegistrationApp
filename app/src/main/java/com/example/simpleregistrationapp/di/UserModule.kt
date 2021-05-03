package com.example.simpleregistrationapp.di

import android.app.Application
import com.example.simpleregistrationapp.R
import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.confirmation.GetUserUseCase
import com.example.simpleregistrationapp.feature.confirmation.GetUserUseCaseImpl
import com.example.simpleregistrationapp.feature.registration.RegisterNewUserUseCase
import com.example.simpleregistrationapp.feature.registration.RegisterNewUserUseCaseImpl
import com.example.simpleregistrationapp.feature.registration.RegistrationLiterals
import com.example.simpleregistrationapp.storage.user.RoomUserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    fun coroutinesDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    fun provideUserStorage(roomStorage: RoomUserStorage): UserStorage {
        return roomStorage
    }

    @Provides
    fun provideRegistrationUseCase(registerNewUserUseCase: RegisterNewUserUseCaseImpl): RegisterNewUserUseCase {
        return registerNewUserUseCase
    }

    @Provides
    fun provideGetUseCase(getUserUseCase: GetUserUseCaseImpl): GetUserUseCase {
        return getUserUseCase
    }

    @Provides
    fun registrationLiterals(context: Application): RegistrationLiterals =
        with(context) {
            RegistrationLiterals(
                emptyNameError = getString(R.string.validator_empty_name),
                invalidNameError = getString(R.string.validator_invalid_name),
                emptyEmailError = getString(R.string.validator_empty_email),
                invalidEmailError = getString(R.string.validator_invalid_email),
                emptyDateError = getString(R.string.validator_empty_date_of_birth),
                invalidDateError = getString(R.string.validator_invalid_date_of_birth)
            )
        }

}
