package com.paulinoo.sshclient.manager.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "SSHClientManager")
data class SSHClientManager(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    var name: String,
    var host: String,
    var username: String,
    var password: String,
    var port: String,
    @TypeConverters(Converters::class) var commands: LinkedHashMap<String, String>
)

fun SSHClientManager.copy( name: String = this.name, host: String = this.host, username: String = this.username, password: String = this.password, port: String = this.port, commands: LinkedHashMap<String, String> = this.commands): SSHClientManager {
    return SSHClientManager(this.uid, name, host, username, password, port, commands)
}

