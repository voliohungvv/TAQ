package com.hdteam.appquality.taq.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hdteam.appquality.taq.data.local.model.LogLocal

/***
Create by HungVV
Create at 17:14/11-09-2024
 ***/
private const val TAG = "LogDAO"

@Dao
internal interface LogDAO {
    @Query("SELECT * FROM log_local")
    suspend fun getAll(): List<LogLocal>

//    @Query("SELECT * FROM log_local WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

//    @Query("SELECT * FROM log_local WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    suspend fun insertAll(vararg logLocal: LogLocal):List<Long>

    @Delete
    suspend fun delete(logLocal: LogLocal)
}