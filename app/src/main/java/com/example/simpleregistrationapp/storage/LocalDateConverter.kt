package com.example.simpleregistrationapp.storage

import androidx.room.TypeConverter
import com.example.simpleregistrationapp.utils.LOCAL_DATE_FORMAT
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class LocalDateConverter {
    private val dateFormatter = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT)

    @TypeConverter
    fun localDateFromString(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(value, dateFormatter) }
    }

    @TypeConverter
    fun localDateToString(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }
}
