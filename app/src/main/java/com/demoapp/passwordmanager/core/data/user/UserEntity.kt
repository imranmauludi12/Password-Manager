package com.demoapp.passwordmanager.core.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "user_table", indices = [Index(value = ["master_key"], unique = true)])
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    val Id: Int = 0,

    @NotNull
    @ColumnInfo(name = "master_key")
    val accountKey: String,

    @NotNull
    @ColumnInfo(name = "account_name")
    val accountName: String,

    @NotNull
    @ColumnInfo(name = "account_email")
    val accountEmail: String

)
