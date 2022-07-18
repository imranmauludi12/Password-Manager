package com.demoapp.passwordmanager.core.data

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import com.demoapp.passwordmanager.core.data.user.UserDao
import com.demoapp.passwordmanager.core.data.user.UserEntity

class UserRepository private constructor(private val dao: UserDao) {

    fun checkKeyExist(key: String): LiveData<UserEntity> = dao.checkMasterKey(key)
    fun getProfile(userId: Int): LiveData<UserEntity> = dao.getProfile(id = userId)

    suspend fun addNewAccount(acc: UserEntity) = dao.createNewAccount(acc)

    suspend fun deleteAcc(acc: UserEntity) = dao.deleteAcc(acc)

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val database = AppDatabase.getInstance(context)
                UserRepository(database.userDao())
            }
        }
    }
}