package com.paulinoo.sshclient.manager.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.paulinoo.sshclient.manager.database.SSHClientManager
import com.paulinoo.sshclient.manager.database.SSHClientManagerDAO


class SSHClientManagerRepository(private val sshClientManagerDAO: SSHClientManagerDAO) {

    suspend fun insert(sshClientManager: SSHClientManager) {
        sshClientManagerDAO.insert(sshClientManager)
    }

    suspend fun delete(uid: Int) {
        sshClientManagerDAO.delete(uid)
    }

    suspend fun update(sshClientManager: SSHClientManager) {
        try {
            sshClientManagerDAO.update(
                sshClientManager.uid,
                sshClientManager.name,
                sshClientManager.host,
                sshClientManager.username,
                sshClientManager.password,
                sshClientManager.port.toInt()
            )
            Log.d("SSHClientManagerRepo", "Update successful for client: ${sshClientManager.uid}")
        } catch (e: Exception) {
            Log.e("SSHClientManagerRepo", "Update failed for client: ${sshClientManager.uid} with error: ${e.message}")
        }
    }

    suspend fun getById(uid: Int): SSHClientManager? {
        return sshClientManagerDAO.getById(uid)
    }

    /*
    suspend fun getCommands(): List<SSHClientManager> {
        return sshClientManagerDAO.getAll()
    }*/

    suspend fun getAllClients(): LiveData<List<SSHClientManager>> {
        return sshClientManagerDAO.getAll()
    }
}
