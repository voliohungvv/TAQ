package com.hdteam.appquality.taq.utils.util

import android.util.Base64
import android.util.Log
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

private const val TAG = "EndCodeUtils"

internal object EndCodeUtils {

    const val transformation = "AES/CBC/PKCS5Padding"
    const val algorithm = "AES"
    var mailBack = "SHVuZ1ZWQGdvdm8xMjM0NQ=="

    fun encrypt(key: String, vec: String, data: String): String {
        val keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), algorithm)
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(vec.toByteArray(Charsets.UTF_8)))
        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedData, Base64.NO_WRAP)
    }


    fun decrypt(key: String, vec: String, encodedData: String?): String {
        return try {
            val keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), algorithm)
            val cipher = Cipher.getInstance(transformation)
            cipher.init(
                Cipher.DECRYPT_MODE,
                keySpec,
                IvParameterSpec(vec.toByteArray())
            )
            val decodedData = Base64.decode(encodedData, Base64.NO_WRAP)
            String(cipher.doFinal(decodedData))
        } catch (e: Exception) {
            Log.e(TAG, "--------------------------------->decrypt ERR: ${e.toString()}")
            ""
        }
    }


}