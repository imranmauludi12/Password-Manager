package com.demoapp.passwordmanager.core.data.password

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "password_entity")
data class PasswordEntity(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @NotNull
    @ColumnInfo(name = "app_name")
    val appName: String,

    @NotNull
    @ColumnInfo(name = "password")
    val password: String,

    @NotNull
    @ColumnInfo(name = "email_address")
    val emailAddress: String,

    @NotNull
    @ColumnInfo(name = "last_edit")
    val date: String,

    @NotNull
    @ColumnInfo(name = "account_id")
    val accId: Int

)