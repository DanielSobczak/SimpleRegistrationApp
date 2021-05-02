package com.example.simpleregistrationapp.storage

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class LocalDateConverter {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @TypeConverter
    fun localDateFromString(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value, dateFormatter) }
    }

    @TypeConverter
    fun localDateToString(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }
}
