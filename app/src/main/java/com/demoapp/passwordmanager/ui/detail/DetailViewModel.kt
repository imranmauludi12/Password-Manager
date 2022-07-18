package com.demoapp.passwordmanager.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demoapp.passwordmanager.core.data.PasswordRepository
import com.demoapp.passwordmanager.core.data.password.PasswordEntity
import kotlinx.coroutines.launch
import java.io.IOException

class DetailViewModel constructor(private val repository: PasswordRepository): ViewModel() {

    fun getDetailCredential(id: Int): LiveData<PasswordEntity> {
        return repository.getDetailPassword(id)
    }

    fun deleteCredential(item: PasswordEntity) = viewModelScope.launch {
        repository.deletePass(item)
    }

    fun changePasswordCredential(id: Int, newPassword: String) = viewModelScope.launch {
        try {
            repository.generateNewPassword(id, newPassword)
        } catch (e: IOException) {
            Log.d(TAG, "I/O error: ${e.message.toString()}")
        } catch (e: Exception) {
            Log.d(TAG, "error: ${e.message.toString()}")
        }
    }

    companion object {
        private const val TAG = "cek detailViewModel"
    }

}