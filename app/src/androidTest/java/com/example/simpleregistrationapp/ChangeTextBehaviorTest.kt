package com.example.simpleregistrationapp

import android.widget.DatePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers
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
        val name = "Foo"
        val email = "foo@bar.com"
        onView(withId(R.id.registration_input_name))
            .perform(typeText(name))
        onView(withId(R.id.registration_input_email))
            .perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.registration_input_date))
            .perform(click())

        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(
                2017,
                6,
                30
            )
        )
        onView(withText(android.R.string.ok)).perform(click())

        onView(withId(R.id.registration_btn_register)).perform(click())

        onView(withId(R.id.confirmation_user_name))
            .check(matches(withText(name)))
        onView(withId(R.id.confirmation_user_mail))
            .check(matches(withText(email)))
        onView(withId(R.id.confirmation_user_date_of_birth))
            .check(matches(withText("30/06/2017")))
    }
}
