package com.example.simpleregistrationapp.feature.confirmation

import android.view.View
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.simpleregistrationapp.R
import com.example.simpleregistrationapp.databinding.FragmentConfirmationBinding
import com.example.simpleregistrationapp.feature.utils.LoadingState
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ConfirmationFragment : Fragment(R.layout.fragment_confirmation), MavericksView {
    private val binding by viewBinding(FragmentConfirmationBinding::bind)
    private val viewModel: ConfirmationViewModel by fragmentViewModel()

    override fun invalidate() {
        withState(viewModel) { state ->
            when (state.loadingState) {
                LoadingState.Loading -> renderLoading()
                LoadingState.Ready -> renderReadyState(state)
                LoadingState.Error -> TODO()
            }
        }
    }

    private fun renderLoading() {
        with(binding) {
            confirmationLoader.visibility = View.VISIBLE
            confirmationContent.visibility = View.GONE
        }
    }

    private fun renderReadyState(state: ConfirmationState) {
        with(binding) {
            confirmationLoader.visibility = View.GONE
            confirmationContent.visibility = View.VISIBLE
            confirmationUserName.text = state.user?.name
            confirmationUserMail.text = state.user?.email
            confirmationUserDateOfBirth.text = state.user?.dateOfBirth.toString()
        }
    }

}
