package com.demoapp.passwordmanager.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.commonsware.cwac.saferoom.SafeHelperFactory
import com.demoapp.passwordmanager.core.data.password.PasswordDao
import com.demoapp.passwordmanager.core.data.password.PasswordEntity
import com.demoapp.passwordmanager.core.data.user.UserDao
import com.demoapp.passwordmanager.core.data.user.UserEntity

@Database(version = 1, entities = [PasswordEntity::class, UserEntity::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun passwordDao(): PasswordDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Password.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}