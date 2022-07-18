package com.demoapp.passwordmanager.core.data.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table where master_key = :key")
    fun checkMasterKey(key: String): LiveData<UserEntity>

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getProfile(id: Int): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createNewAccount(vararg acc: UserEntity)

    @Delete
    suspend fun deleteAcc(acc: UserEntity)

}