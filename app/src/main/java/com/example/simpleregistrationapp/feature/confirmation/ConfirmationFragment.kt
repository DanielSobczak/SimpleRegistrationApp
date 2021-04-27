package com.example.simpleregistrationapp.feature.confirmation

import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.simpleregistrationapp.R
import com.example.simpleregistrationapp.databinding.FragmentConfirmationBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

class ConfirmationFragment : Fragment(R.layout.fragment_confirmation), MavericksView {
    private val binding by viewBinding(FragmentConfirmationBinding::bind)
    private val viewModel: ConfirmationViewModel by fragmentViewModel()

    override fun invalidate() {
        withState(viewModel) { state ->
            with(binding) {
                confirmationUserName.text = state.dummy
                confirmationUserMail.text = state.dummy
                confirmationUserDateOfBirth.text = state.dummy
            }
        }
    }

}
