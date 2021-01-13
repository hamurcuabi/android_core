package com.lagina.mvvmcore.data.local.typeconverter

import androidx.room.TypeConverter
import java.util.*

/**Created by hamurcuabi on 03,December,2020 **/
class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}