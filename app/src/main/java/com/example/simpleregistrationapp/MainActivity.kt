package com.example.simpleregistrationapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.simpleregistrationapp.databinding.FragmentRegistrationBinding
import com.example.simpleregistrationapp.feature.registration.RegistrationViewModel
import com.example.simpleregistrationapp.feature.registration.ValidationResponse.ValidationError.*
import com.example.simpleregistrationapp.feature.utils.onTextChanged
import com.example.simpleregistrationapp.feature.utils.updateTextIfDifferent
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class RegistrationFragment : Fragment(R.layout.fragment_registration), MavericksView {
    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel: RegistrationViewModel by fragmentViewModel()


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
                registrationInputEmail.updateTextIfDifferent(state.email)
                registrationInputDate.updateTextIfDifferent(state.formattedDateOfBirth)

                registrationInputEmailContainer.apply {
                    if (state.formErrors.any { it is IncorrectEmailFormat }) {
                        error = "Incorrect Email Format"
                    }
                }

                state.formErrors.forEach {
                    when (it) {
                        is IncorrectEmailFormat -> registrationInputEmailContainer.error =
                            "Incorrect Email Format"
                        is MissingDateOfBirth -> registrationInputDateContainer.error =
                            "Missing date of birth"
                        is MissingEmail -> registrationInputEmailContainer.error = "Missing email"
                        is MissingName -> registrationInputNameContainer.error = "Missing name"
                    }
                }

                if (!state.formErrors.any { it is IncorrectEmailFormat || it is MissingEmail }) {
                    registrationInputEmailContainer.error = null
                }

                if (!state.formErrors.any { it is MissingDateOfBirth }) {
                    registrationInputDateContainer.error = null
                }

                if (!state.formErrors.any { it is MissingName }) {
                    registrationInputNameContainer.error = null
                }
            }
        }
    }

}
