package com.example.simpleregistrationapp.di

import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.registration.RegisterNewUserUseCase
import com.example.simpleregistrationapp.feature.registration.RegisterNewUserUseCaseImpl
import com.example.simpleregistrationapp.storage.user.RoomUserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    fun provideUserStorage(roomStorage: RoomUserStorage): UserStorage {
        return roomStorage
    }

    @Provides
    fun provideRegistrationUseCase(registerNewUserUseCase: RegisterNewUserUseCaseImpl): RegisterNewUserUseCase {
        return registerNewUserUseCase
    }

}
