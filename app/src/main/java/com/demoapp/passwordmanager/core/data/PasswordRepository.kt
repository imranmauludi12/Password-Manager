package com.demoapp.passwordmanager.core.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.demoapp.passwordmanager.core.data.password.PasswordDao
import com.demoapp.passwordmanager.core.data.password.PasswordEntity
import com.demoapp.passwordmanager.core.utils.FilterUtils
import com.demoapp.passwordmanager.core.utils.PasswordFilterType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class PasswordRepository private constructor(private val dao: PasswordDao) {

    suspend fun insertNewPassword(item: PasswordEntity) = dao.insertNewPassword(item)

    suspend fun deletePass(item: PasswordEntity) = dao.deletePassword(item)

    suspend fun generateNewPassword(passId: Int, newPassword: String) = dao.changePassword(passId, newPassword)

    fun getDetailPassword(id: Int): LiveData<PasswordEntity> {
        return dao.detailPassword(id)
    }

    // TODO: GET LIST OF PASSWORD NOT IMPLEMENTED


    fun getAllPassword(id: Int): Flow<List<PasswordEntity>> = flow {
        val list = ArrayList<PasswordEntity>()
        val data = dao.getAllPassword(id)
        data.map {
            list.addAll(it.value)
        }
        emit(list)
    }

//    suspend fun getPasswordStateFlow(id: Int): StateFlow<List<PasswordEntity>> {
//        val stateContainer = MutableStateFlow<List<PasswordEntity>>(emptyList())
//        val data = dao.getAllPassword(id)
//        data.forEach {
//            stateContainer.value = it.value
//        }
//        return stateContainer
//    }

    suspend fun getPasswordStateFlow(id: Int): StateFlow<List<PasswordEntity>> = flow {
        val list = ArrayList<PasswordEntity>()
        val data = dao.getAllPassword(id)
        data.map {
            list.addAll(it.value)
        }
        emit(list)
    }.stateIn(CoroutineScope(Job() + Dispatchers.IO))


    companion object {
        @Volatile
        var INSTANCE: PasswordRepository? = null

        fun getInstance(context: Context): PasswordRepository =
            INSTANCE ?: synchronized(this) {
                val database = AppDatabase.getInstance(context)
                PasswordRepository(database.passwordDao())
            }
    }
}