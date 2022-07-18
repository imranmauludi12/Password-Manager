package com.demoapp.passwordmanager.core.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demoapp.passwordmanager.core.data.UserRepository
import com.demoapp.passwordmanager.ui.auth.login.LoginViewModel
import com.demoapp.passwordmanager.ui.auth.register.RegisterViewModel
import com.demoapp.passwordmanager.ui.home.profile.ProfileViewModel

class AuthenticationVMFactory private constructor(private val repository: UserRepository): ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: AuthenticationVMFactory? = null

        fun getInstance(context: Context): AuthenticationVMFactory =
            instance ?: synchronized(this) {
                instance ?: AuthenticationVMFactory(UserRepository.getInstance(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            else -> {
                throw Throwable("Unknown model class name ${modelClass.name}")
            }
        }
    }
}