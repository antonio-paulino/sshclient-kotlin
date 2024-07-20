package com.paulinoo.sshclient.manager.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromCommands(commands: HashMap<String, String>): String {
        return Gson().toJson(commands)
    }

    @TypeConverter
    fun toCommands(data: String): HashMap<String, String> {
        val type = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().fromJson(data, type)
    }
}