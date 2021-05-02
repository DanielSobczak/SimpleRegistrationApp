package com.example.simpleregistrationapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.example.simpleregistrationapp.screens.ConfirmationScreen
import com.example.simpleregistrationapp.screens.RegistrationScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RegistrationWalkthroughTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun registerNewUserWithValidInputData_willShowConfirmationScreen() {
        val nameInput = "Foo"
        val emailInput = "foo@bar.com"

        onScreen<RegistrationScreen> {
            name.edit.typeText(nameInput)
            email.edit.typeText(emailInput)
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

    @Test
    fun registerNewUserWithoutName_willShowError() {
        val emailInput = "foo@bar.com"

        onScreen<RegistrationScreen> {
            email.edit.typeText(emailInput)
            closeSoftKeyboard()
            dateOfBirth.click()
            calendar {
                datePicker.setDate(2017, 6, 30)
                okButton.click()
            }
            register.click()

            name.hasError("Missing username")
        }
    }

    @Test
    fun registerNewUserWithoutEmail_willShowError() {
        val nameInput = "Foo"

        onScreen<RegistrationScreen> {
            name.edit.typeText(nameInput)
            closeSoftKeyboard()
            dateOfBirth.click()
            calendar {
                datePicker.setDate(2017, 6, 30)
                okButton.click()
            }
            register.click()

            email.hasError("Missing email")
        }
    }

    @Test
    fun registerNewUserWithoutDate_willShowError() {
        val nameInput = "Foo"
        val emailInput = "foo@bar.com"

        onScreen<RegistrationScreen> {
            name.edit.typeText(nameInput)
            email.edit.typeText(emailInput)
            closeSoftKeyboard()
            register.click()

            dateOfBirth.hasError("Missing date of birth")
        }
    }
}



