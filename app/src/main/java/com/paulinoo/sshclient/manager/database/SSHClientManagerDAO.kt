package com.paulinoo.sshclient.manager.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SSHClientManagerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sshClientManager: SSHClientManager)

    @Query("DELETE FROM SSHClientManager WHERE uid = :uid")
    suspend fun delete(uid: Int)

    @Query("UPDATE SSHClientManager SET name = :name, host = :host, username = :username, password = :password, port = :port WHERE uid = :uid")
    suspend fun update(uid: Int, name: String, host: String, username: String, password: String, port: Int)

    @Query("SELECT * FROM SSHClientManager ORDER BY uid ASC")
    fun getAll(): LiveData<List<SSHClientManager>>

    @Query("SELECT * FROM SSHClientManager WHERE uid = :uid")
    suspend fun getById(uid: Int): SSHClientManager?
}
