package com.demoapp.passwordmanager.core.data.password

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.demoapp.passwordmanager.core.data.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPassword(item: PasswordEntity)

    @Query("UPDATE password_entity SET password = :newPassword WHERE id = :itemId")
    suspend fun changePassword(itemId: Int, newPassword: String)

    @Delete
    suspend fun deletePassword(item: PasswordEntity)

    @Query("SELECT * FROM password_entity WHERE id = :id")
    fun detailPassword(id: Int): LiveData<PasswordEntity>

    @RawQuery(observedEntities = [PasswordEntity::class, UserEntity::class])
    fun getAllPasswordWithFilter(query: SupportSQLiteQuery): Map<UserEntity, List<PasswordEntity>>

    @Query("SELECT * FROM user_table " +
            "JOIN password_entity ON user_table.id = password_entity.account_id " +
            "WHERE user_table.id = :id GROUP BY app_name")
    suspend fun getAllPassword(id: Int): Map<UserEntity, List<PasswordEntity>>



}