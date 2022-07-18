package com.demoapp.passwordmanager.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demoapp.passwordmanager.core.data.PasswordRepository
import com.demoapp.passwordmanager.core.data.password.PasswordEntity
import kotlinx.coroutines.launch

class AddPasswordViewModel constructor(private val repository: PasswordRepository): ViewModel() {

    fun createNewPassword(item: PasswordEntity) = viewModelScope.launch {
        repository.insertNewPassword(item)
    }

}