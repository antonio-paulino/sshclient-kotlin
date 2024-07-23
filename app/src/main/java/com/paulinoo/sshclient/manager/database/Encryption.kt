package com.paulinoo.sshclient.manager.database

import android.annotation.SuppressLint
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@SuppressLint("GetInstance")
fun encrypt(data: String, key: SecretKeySpec): String {
    val cipher = Cipher.getInstance("AES")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
    return Base64.encodeToString(encryptedData, Base64.DEFAULT)
}

@SuppressLint("GetInstance")
fun decrypt(data: String, key: SecretKeySpec): String {
    val cipher = Cipher.getInstance("AES")
    cipher.init(Cipher.DECRYPT_MODE, key)
    val decodedData = Base64.decode(data, Base64.DEFAULT)
    return String(cipher.doFinal(decodedData), Charsets.UTF_8)
}
