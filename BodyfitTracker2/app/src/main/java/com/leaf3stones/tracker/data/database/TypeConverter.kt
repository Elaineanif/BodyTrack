package com.leaf3stones.tracker.data.database

import androidx.room.TypeConverter
import java.util.Date

class TypeConverter {
    @TypeConverter
    fun fromDate(date:Date?) :Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(time:Long?) : Date? {
        return time?.let {
            Date(it)
        }
    }
}