package com.paulinoo.sshclient.manager

import com.paulinoo.sshclient.manager.database.SSHClientManager
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier


class Runner(private val sshClient: SSHClientManager) {

        fun runSingleCommand(name: String) {
            val client = SSHClient()
            try {
                client.addHostKeyVerifier(PromiscuousVerifier())
                client.connect(sshClient.host, sshClient.port.toInt())
                client.authPassword(sshClient.username, sshClient.password)

                val command = sshClient.commands.hashMap[name]
                client.startSession().use { session ->
                    val cmd = session.exec(command)
                    println(cmd.inputStream.bufferedReader().readText())
                    cmd.join()  // Ensure the command execution completes
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                client.disconnect()
            }
        }

    fun runAllCommands() {
        val client = SSHClient()
        try {
            client.addHostKeyVerifier(PromiscuousVerifier())
            client.connect(sshClient.host, sshClient.port.toInt())
            client.authPassword(sshClient.username, sshClient.password)

            for (command in sshClient.commands.hashMap.values) {
                client.startSession().use { session ->
                    val cmd = session.exec(command)
                    println(cmd.inputStream.bufferedReader().readText())
                    cmd.join()  // Ensure the command execution completes
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            client.disconnect()
        }
    }
}
