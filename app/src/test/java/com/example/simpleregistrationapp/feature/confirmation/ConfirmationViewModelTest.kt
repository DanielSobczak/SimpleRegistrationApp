package com.example.simpleregistrationapp.feature.confirmation

import com.airbnb.mvrx.withState
import com.example.simpleregistrationapp.feature.utils.LoadingState
import com.example.simpleregistrationapp.feature.utils.MavericksViewModelTestExtension
import com.example.simpleregistrationapp.feature.utils.dummyUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@ExtendWith(MavericksViewModelTestExtension::class)
internal class ConfirmationViewModelTest {

    private val mockGetUseCase = mock<GetUserUseCase>()

    @Test
    fun `given user exist When viewmodel is launched Then state is reduced with success`() =
        runBlockingTest {
            givenUseCaseReturns(FetchUserResult.Success(dummyUser))
            val initialState = ConfirmationState()
            withState(ConfirmationViewModel(initialState, mockGetUseCase)) {
                assertThat(it).isEqualTo(
                    initialState.copy(
                        loadingState = LoadingState.Ready,
                        user = dummyUser
                    )
                )
            }
        }

    @Test
    fun `given user does not exist When viewmodel is launched Then state is reduced with error`() =
        runBlockingTest {
            givenUseCaseReturns(FetchUserResult.NoUserFound)
            val initialState = ConfirmationState()
            withState(ConfirmationViewModel(initialState, mockGetUseCase)) {
                assertThat(it).isEqualTo(
                    initialState.copy(
                        loadingState = LoadingState.Error
                    )
                )
            }
        }

    @Test
    fun `given unhandled error When viewmodel is launched Then state is reduced with error`() =
        runBlockingTest {
            givenUseCaseReturns(FetchUserResult.UnhandledError(RuntimeException()))
            val initialState = ConfirmationState()
            withState(ConfirmationViewModel(initialState, mockGetUseCase)) {
                assertThat(it).isEqualTo(
                    initialState.copy(
                        loadingState = LoadingState.Error
                    )
                )
            }
        }

    private fun givenUseCaseReturns(vararg result: FetchUserResult) {
        given(mockGetUseCase.getRegisteredUser()).willReturn(flowOf(*result))
    }

}
