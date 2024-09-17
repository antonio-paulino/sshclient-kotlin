package com.paulinoo.sshclient.manager.database

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

fun encrypt(data: String, secretKey: SecretKey): String {
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    val iv = cipher.iv
    val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
    return iv.plus(encryptedData).toBase64() // Combine IV and encrypted data
}

fun decrypt(data: String, secretKey: SecretKey): String {
    val dataBytes = data.fromBase64()
    val iv = dataBytes.copyOfRange(0, 16)
    val encryptedData = dataBytes.copyOfRange(16, dataBytes.size)
    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
    val decryptedData = cipher.doFinal(encryptedData)
    return String(decryptedData, Charsets.UTF_8)
}

// Helper extension functions for Base64 encoding/decoding
fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.NO_WRAP)
fun String.fromBase64(): ByteArray = Base64.decode(this, Base64.NO_WRAP)



fun generateAndStoreSecretKey(alias: String, context: Context): SecretKey {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        if (!keyStore.containsAlias(alias)) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setKeySize(256)
                .build()
            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }

        val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        secretKeyEntry.secretKey
    } else {
        val prefs = context.getSharedPreferences("secret_key_prefs", Context.MODE_PRIVATE)
        val existingKey = prefs.getString(alias, null)
        if (existingKey == null) {
            val random = SecureRandom()
            val keyBytes = ByteArray(16) // 128-bit key
            random.nextBytes(keyBytes)
            val newKey = SecretKeySpec(keyBytes, "AES")
            val encodedKey = Base64.encodeToString(newKey.encoded, Base64.NO_WRAP)
            prefs.edit().putString(alias, encodedKey).apply()
            newKey
        } else {
            val decodedKey = Base64.decode(existingKey, Base64.NO_WRAP)
            SecretKeySpec(decodedKey, "AES")
        }
    }
}

