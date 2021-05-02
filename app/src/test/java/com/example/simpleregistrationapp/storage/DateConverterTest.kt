package com.example.simpleregistrationapp.storage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate

class DateConverterTest {

    private val sut = LocalDateConverter()

    @Test
    fun convertingDateToString() {
        val result = sut.localDateToString(LocalDate.of(1900, 3, 25))
        assertThat(result).isEqualTo("25/03/1900")
    }

    @Test
    fun convertingStringFromDate() {
        val result = sut.localDateFromString("25/03/1900")
        assertThat(result).isEqualTo(LocalDate.of(1900, 3, 25))
    }

}
