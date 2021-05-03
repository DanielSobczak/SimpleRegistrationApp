package com.example.simpleregistrationapp.feature.utils

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

open class ArgumentsMapProvider(private val arguments: Map<*, *>) : ArgumentsProvider {

    constructor(vararg argument: Pair<*, *>) : this(argument.toMap())

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        @Suppress("NewApi")
        return arguments.map { entry -> arguments(entry.key, entry.value) }.stream()
    }
}
