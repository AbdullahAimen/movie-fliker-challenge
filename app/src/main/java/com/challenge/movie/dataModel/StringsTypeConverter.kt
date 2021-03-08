package com.challenge.movie.dataModel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringsTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().toJson(list, type)
    }
}