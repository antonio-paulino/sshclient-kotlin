package com.paulinoo.sshclient.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.paulinoo.sshclient.manager.database.SSHClientManager
import com.paulinoo.sshclient.manager.database.AppDatabase
import com.paulinoo.sshclient.manager.repository.SSHClientManagerRepository
import kotlinx.coroutines.launch

class SSHClientManagerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SSHClientManagerRepository
    lateinit var allClients: LiveData<List<SSHClientManager>>

    init {
        val sshClientManagerDAO = AppDatabase.getDatabase(application).sshClientManagerDAO()
        repository = SSHClientManagerRepository(sshClientManagerDAO)
        loadAllClients()
    }
    private fun loadAllClients() = viewModelScope.launch {
        allClients = repository.getAllClients()
    }

    fun insert(sshClientManager: SSHClientManager) = viewModelScope.launch {
        repository.insert(sshClientManager)
    }

    fun delete(uid: Int) = viewModelScope.launch {
        repository.delete(uid)
    }

    suspend fun getById(uid: Int): SSHClientManager? = repository.getById(uid)

    fun update(sshClientManager: SSHClientManager) = viewModelScope.launch {
        repository.update(sshClientManager)
    }

    /*
    suspend fun getCommands(): List<SSHClientManager> {
        return repository.getCommands()
    }

     */
}
