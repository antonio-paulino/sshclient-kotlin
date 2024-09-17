package com.paulinoo.sshclient.manager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paulinoo.sshclient.manager.database.SSHClientManager
import com.paulinoo.sshclient.manager.database.AppDatabase
import com.paulinoo.sshclient.manager.database.encrypt
import com.paulinoo.sshclient.manager.database.decrypt
import com.paulinoo.sshclient.manager.database.generateAndStoreSecretKey
import com.paulinoo.sshclient.manager.repository.SSHClientManagerRepository
import kotlinx.coroutines.launch
import javax.crypto.SecretKey

class SSHClientManagerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SSHClientManagerRepository
    var allClients: LiveData<List<SSHClientManager>>
    private val secretKeyAlias = "SSHClientManagerKey"
    private var secretKey: SecretKey

    init {
        val sshClientManagerDAO = AppDatabase.getDatabase(application).sshClientManagerDAO()
        repository = SSHClientManagerRepository(sshClientManagerDAO)
        secretKey = generateAndStoreSecretKey(secretKeyAlias, getApplication<Application>().applicationContext)
        allClients = loadAllClients()
    }

    private fun loadAllClients(): LiveData<List<SSHClientManager>> = liveData {
        val decryptedClients = repository.getAllClients().map { client ->
            client.map {
                it.apply {
                    password = decrypt(password, secretKey)
                    username = decrypt(username, secretKey)
                }

            }
        }
        emitSource(decryptedClients)
    }

    fun insert(sshClientManager: SSHClientManager) = viewModelScope.launch {
        sshClientManager.apply {
            password = encrypt(password, secretKey)
            username = encrypt(username, secretKey)
        }
        repository.insert(sshClientManager)
    }

    fun delete(uid: Int) = viewModelScope.launch {
        repository.delete(uid)
    }

    suspend fun getById(uid: Int): SSHClientManager? {
        return repository.getById(uid)?.apply {
            password = decrypt(password, secretKey)
            username = decrypt(username, secretKey)
        }
    }

    fun update(sshClientManager: SSHClientManager) = viewModelScope.launch {
        sshClientManager.apply {
            password = encrypt(password, secretKey)
            username = encrypt(username, secretKey)
        }
        repository.update(sshClientManager)
    }




    /*
    suspend fun getCommands(): List<SSHClientManager> {
        // If this function retrieves sensitive data, ensure to decrypt it before returning
    }
    */
}