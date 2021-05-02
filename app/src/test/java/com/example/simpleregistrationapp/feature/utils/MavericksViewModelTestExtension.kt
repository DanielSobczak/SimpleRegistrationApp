package com.example.simpleregistrationapp.feature.utils

import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksViewModelConfigFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class MavericksViewModelTestExtension(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    override fun beforeAll(context: ExtensionContext?) {
        Mavericks.initialize(
            debugMode = false,
            viewModelConfigFactory = MavericksViewModelConfigFactory(
                true,
                contextOverride = dispatcher,
                storeContextOverride = dispatcher,
                subscriptionCoroutineContextOverride = dispatcher
            )
        )
    }

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
