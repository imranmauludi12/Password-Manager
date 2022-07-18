package com.demoapp.passwordmanager.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder


enum class PasswordFilterType {
    ALL_PASSWORD,

    ORDER_BY_NAME,

    ORDER_BY_RECENT_EDIT
}

object FilterUtils {

    fun getQuery(type: PasswordFilterType, userId: Int = 0): SimpleSQLiteQuery {
        val builder = StringBuilder()
            .append("SELECT * FROM $USER_TABLE ")
            .append("JOIN $PASSWORD_TABLE ON $USER_TABLE.id = $PASSWORD_TABLE.account_id ")
        when (type) {
            PasswordFilterType.ORDER_BY_NAME -> builder.append("ORDER BY $PASSWORD_TABLE.app_name ASC")
            PasswordFilterType.ORDER_BY_RECENT_EDIT -> builder.append("ORDER BY $PASSWORD_TABLE.last_edit ASC")
            else -> {}
        }
        return SimpleSQLiteQuery(builder.toString())
    }

}

