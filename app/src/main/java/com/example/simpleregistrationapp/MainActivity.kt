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
import com.example.simpleregistrationapp.feature.utils.onChange
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
        binding.registrationInputName.onChange {
            viewModel.updateName(it)
        }
    }

    override fun invalidate() {
        withState(viewModel) { state ->
            binding.registrationInputDate.setText(state.name)
        }
    }

}
