package com.paulinoo.sshclient.manager

import com.paulinoo.sshclient.manager.database.SSHClientManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier

class Runner(private val sshClient: SSHClientManager) {

    fun runSingleCommand(name: String) {
        val client = SSHClient()
        try {
            client.addHostKeyVerifier(PromiscuousVerifier())
            client.connect(sshClient.host, sshClient.port.toInt())
            client.authPassword(sshClient.username, sshClient.password)

            val command = sshClient.commands.getValue(name)
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

            sshClient.commands.forEach { (_, command) ->
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

/*
fun main() = runBlocking {
    val sshClient = SSHClientManager(
        0,
        "Test",
        "192.168.1.74",
        "pi",
        "raspberrypi",
        "22",
        linkedMapOf()
    )

    sshClient.commands["ls"] = "ls -la"
    sshClient.commands["ping"] = "ping -c 4 google.com"

    sshClient.commands.forEach(::println)
    val runner = Runner(sshClient)
    runner.runAllCommands()

}

 */
