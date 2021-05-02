package com.example.simpleregistrationapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.picker.date.KDatePickerDialog
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangeTextBehaviorTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun registerNewUserWithValidInputData_willShowConfirmationScreen() {
        val nameInput = "Foo"
        val emailInput = "foo@bar.com"

        onScreen<RegistrationScreen> {
            name.typeText(nameInput)
            email.typeText(emailInput)
            closeSoftKeyboard()
            dateOfBirth.click()
            calendar {
                datePicker.setDate(2017, 6, 30)
                okButton.click()
            }
            register.click()
        }

        onScreen<ConfirmationScreen> {
            name.hasText(nameInput)
            email.hasText(emailInput)
            dateOfBirth.hasText("30/06/2017")
        }
    }
}

class RegistrationScreen : Screen<RegistrationScreen>() {
    val name = KEditText { withId(R.id.registration_input_name) }
    val email = KEditText { withId(R.id.registration_input_email) }
    val dateOfBirth = KButton { withId(R.id.registration_input_date) }
    val calendar = KDatePickerDialog()
    val register = KButton { withId(R.id.registration_btn_register) }
}

class ConfirmationScreen : Screen<ConfirmationScreen>() {
    val name = KTextView { withId(R.id.confirmation_user_name) }
    val email = KTextView { withId(R.id.confirmation_user_mail) }
    val dateOfBirth = KTextView { withId(R.id.confirmation_user_date_of_birth) }
}



