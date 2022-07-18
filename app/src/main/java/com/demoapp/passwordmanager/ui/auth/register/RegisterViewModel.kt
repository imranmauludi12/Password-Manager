package com.demoapp.passwordmanager.ui.auth.register

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demoapp.passwordmanager.core.data.UserRepository
import com.demoapp.passwordmanager.core.data.user.UserEntity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {

    fun register(user: UserEntity): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            try {
                repository.addNewAccount(user)
                result.value = true
            } catch (e: SQLiteConstraintException) {
                Log.d("cek registerVM", "cause: ${e.message.toString()}")
                result.value = false
            }
        }
        return result
    }

    fun checkAccount(key: String): LiveData<UserEntity> = repository.checkKeyExist(key)

}