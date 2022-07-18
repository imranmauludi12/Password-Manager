package com.demoapp.passwordmanager.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demoapp.passwordmanager.core.data.UserRepository
import com.demoapp.passwordmanager.core.data.user.UserEntity

class LoginViewModel(private val repository: UserRepository): ViewModel() {

    fun login(key: String): LiveData<UserEntity> = repository.checkKeyExist(key)

}