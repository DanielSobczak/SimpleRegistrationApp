package com.example.simpleregistrationapp.feature.registration

import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.registration.RegistrationResult.InvalidFields
import com.example.simpleregistrationapp.feature.registration.RegistrationResult.Loading
import com.example.simpleregistrationapp.feature.registration.RegistrationResult.Success
import com.example.simpleregistrationapp.feature.registration.RegistrationResult.UnhandledError
import com.example.simpleregistrationapp.feature.registration.validation.UserValidator
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.PassedValidation
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse.ValidationFailed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface RegisterNewUserUseCase {
    fun registerNewUser(request: RegistrationRequest): Flow<RegistrationResult>
}

class RegisterNewUserUseCaseImpl @Inject constructor(
    private val userStorage: UserStorage,
    private val userValidator: UserValidator,
    private val coroutineDispatcher: CoroutineDispatcher
) : RegisterNewUserUseCase {

    override fun registerNewUser(request: RegistrationRequest): Flow<RegistrationResult> =
        flow {
            emit(Loading)
            when (val validationResult = userValidator.validate(request)) {
                PassedValidation -> insertIntoDatabase(request)
                is ValidationFailed -> emit(InvalidFields(validationResult))
            }
        }.flowOn(coroutineDispatcher)

    private suspend fun FlowCollector<RegistrationResult>.insertIntoDatabase(
        request: RegistrationRequest
    ) {
        val user = User(request.name, request.email, request.dateOfBirth!!)
        try {
            userStorage.insert(user)
            emit(Success)
        } catch (exception: Exception) {
            emit(UnhandledError(exception))
        }
    }
}

sealed class RegistrationResult {
    object Loading : RegistrationResult()
    object Success : RegistrationResult()
    data class InvalidFields(val validationError: ValidationFailed) : RegistrationResult()
    data class UnhandledError(val exception: Exception) : RegistrationResult()
}
