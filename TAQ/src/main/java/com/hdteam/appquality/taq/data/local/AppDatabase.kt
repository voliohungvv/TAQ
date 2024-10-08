package com.hdteam.appquality.taq.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hdteam.appquality.taq.data.local.dao.LogDAO
import com.hdteam.appquality.taq.data.local.model.LogLocal


@Database(entities = [LogLocal::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    companion object{
        var appDatabaseName = "tracking-app-quality-hd-database"
    }
    abstract fun localDao(): LogDAO
}