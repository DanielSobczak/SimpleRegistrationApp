package com.example.simpleregistrationapp.di

import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.confirmation.GetUserUseCase
import com.example.simpleregistrationapp.feature.confirmation.GetUserUseCaseImpl
import com.example.simpleregistrationapp.feature.registration.RegisterNewUserUseCase
import com.example.simpleregistrationapp.feature.registration.RegisterNewUserUseCaseImpl
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

}
