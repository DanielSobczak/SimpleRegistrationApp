package com.example.simpleregistrationapp.feature.registration

import com.example.simpleregistrationapp.domain.user.User
import com.example.simpleregistrationapp.domain.user.UserStorage
import com.example.simpleregistrationapp.feature.registration.validation.UserValidator
import com.example.simpleregistrationapp.feature.registration.validation.ValidationResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

sealed class RegistrationResult {
    object Loading : RegistrationResult()
    object Success : RegistrationResult()
    data class InvalidFields(val validationError: ValidationResponse.ValidationFailed) :
        RegistrationResult()

    data class UnhandledError(val exception: Exception) : RegistrationResult()
}

interface RegisterNewUserUseCase {
    fun registerNewUser(request: RegistrationRequest): Flow<RegistrationResult>
}

class RegisterNewUserUseCaseImpl @Inject constructor(
    private val userStorage: UserStorage,
    private val userValidator: UserValidator,
    private val coroutineDispatcher: CoroutineDispatcher
) : RegisterNewUserUseCase {

    override fun registerNewUser(request: RegistrationRequest): Flow<RegistrationResult> =
        flow<RegistrationResult> {
            emit(RegistrationResult.Loading)
            when (val validationResult = userValidator.validate(request)) {
                ValidationResponse.PassedValidation -> insertIntoDatabase(request)
                is ValidationResponse.ValidationFailed -> emit(
                    RegistrationResult.InvalidFields(
                        validationResult
                    )
                )
            }
        }.flowOn(coroutineDispatcher)

    private suspend fun FlowCollector<RegistrationResult>.insertIntoDatabase(
        request: RegistrationRequest
    ) {
        val user = User(request.name, request.email, request.dateOfBirth!!)
        try {
            userStorage.insert(user)
            emit(RegistrationResult.Success)
        } catch (exception: Exception) {
            emit(RegistrationResult.UnhandledError(exception))
        }
    }


}
