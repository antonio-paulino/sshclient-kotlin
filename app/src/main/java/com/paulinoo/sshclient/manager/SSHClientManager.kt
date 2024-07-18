package com.paulinoo.sshclient.manager

data class SSHClientManager(var name: String, var host: String, var username: String, var password: String, var port:String, var commands: HashMap<String, String> = HashMap()) {
    fun addCommand(name: String, command: String) {
        commands[name] = command
    }
    fun removeCommand(name: String) {
        commands.remove(name)
    }
    fun getCommand(name: String): String? {
        return commands[name]
    }
    fun getCommandsNames(): List<String> {
        return commands.keys.toList()
    }
}