package com.paulinoo.sshclient.manager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SSHClientManagerDAO {
    @Insert
    fun insert(sshClientManager: SSHClientManager)

    @Delete
    fun delete(sshClientManager: SSHClientManager)

    @Query("SELECT * FROM SSHClientManager ORDER BY uid ASC")
    fun getAll(): List<SSHClientManager>

    @Query("SELECT * FROM SSHClientManager WHERE uid = :uid")
    fun getById(uid: Int): SSHClientManager
}