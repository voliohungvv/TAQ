package com.hdteam.appquality.taq.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/***
Create by HungVV
Create at 15:25/11-09-2024
 ***/
private const val TAG = "LogLocal"

@Entity(tableName = "log_local")
internal data class LogLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "screen_name")
    val screenName: String?,

    @ColumnInfo(name = "method_name")
    val methodName: String?,

    @ColumnInfo(name = "error")
    val error: String?,

    @ColumnInfo(name = "available_ram")
    val availableRam: String?,

    @ColumnInfo(name = "type_connected")
    val typeConnected: String?,

    @ColumnInfo(name = "session")
    val session: String?,

    @ColumnInfo(name = "version_app")
    val versionApp: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: String?
)