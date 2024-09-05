package com.hdteam.appquality.taq.model

import com.google.gson.Gson

/***
Created by HungVV
Created at 14:32/05-09-2024
 ***/


data class GmailModel(
    val subject: String,
    val body: String,
    val filePathAttach: List<String> = emptyList(),
    val recipients: String,
) {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(json: String?): GmailModel? = try {
            Gson().fromJson(json, GmailModel::class.java)
        } catch (e: Exception) {
            null
        }
    }
}