package com.example.simpleregistrationapp.feature.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.simpleregistrationapp.R
import com.example.simpleregistrationapp.databinding.FragmentRegistrationBinding
import com.example.simpleregistrationapp.feature.utils.onTextChanged
import com.example.simpleregistrationapp.feature.utils.updateTextIfDifferent
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.flow.collect

class RegistrationFragment : Fragment(R.layout.fragment_registration), MavericksView {
    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel: RegistrationViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.sideEffectsFlowReceiver.collect {
                findNavController().navigate(R.id.action_registrationFragment_to_confirmationFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registrationInputName.onTextChanged {
            viewModel.updateName(it)
        }
        binding.registrationInputEmail.onTextChanged {
            viewModel.updateEmail(it)
        }
        binding.registrationInputDate.setOnDatePickedListener {
            viewModel.updateDate(it)
        }
        binding.registrationInputRegister.setOnClickListener {
            viewModel.onRegisterClicked()
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            with(binding) {
                registrationInputName.updateTextIfDifferent(state.name)
                registrationInputNameContainer.error = state.nameError
                registrationInputEmail.updateTextIfDifferent(state.email)
                registrationInputEmailContainer.error = state.emailError
                registrationInputDate.updateTextIfDifferent(state.formattedDateOfBirth)
                registrationInputDateContainer.error = state.dateOfBirthError
            }
        }
    }

}
