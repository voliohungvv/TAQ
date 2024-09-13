package com.hdteam.appquality.taq.di

import android.app.Application
import androidx.room.Room
import com.hdteam.appquality.taq.data.local.AppDatabase

internal object DataModule {

    private var appDatabase: AppDatabase? = null


    private fun provideRoom(appContext: Application): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.appDatabaseName
        ).fallbackToDestructiveMigration().build()
    }


    fun getInstance(appContext: Application): AppDatabase {
        synchronized(this) {
            return appDatabase ?: provideRoom(appContext).also {
                appDatabase = it
            }
        }
    }

}