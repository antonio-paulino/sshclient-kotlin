package com.paulinoo.sshclient.manager.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "SSHClientManager")
data class SSHClientManager(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0, // Auto-generate ID
    var name: String,
    var host: String,
    var username: String,
    var password: String,
    var port: String,
    @TypeConverters(Converters::class) var commands: HashMap<String, String>
)