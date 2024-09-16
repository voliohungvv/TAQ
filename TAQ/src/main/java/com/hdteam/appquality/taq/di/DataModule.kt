package com.hdteam.appquality.taq.di

import android.app.Application
import androidx.room.Room
import com.hdteam.appquality.taq.data.local.AppDatabase

internal object DataModule {

    private var appDatabase: AppDatabase? = null


    private fun provideRoomLog(appContext: Application): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.appDatabaseName
        ).fallbackToDestructiveMigration().build()
    }


    fun getInstanceDatabaseLog(appContext: Application): AppDatabase {
        synchronized(this) {
            return appDatabase ?: provideRoomLog(appContext).also {
                appDatabase = it
            }
        }
    }

    fun closeDatabaseLog() {
        appDatabase?.close()
        appDatabase = null
    }
}