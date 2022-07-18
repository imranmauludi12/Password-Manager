package com.demoapp.passwordmanager.core.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demoapp.passwordmanager.core.data.PasswordRepository
import com.demoapp.passwordmanager.ui.add.AddPasswordViewModel
import com.demoapp.passwordmanager.ui.detail.DetailViewModel
import com.demoapp.passwordmanager.ui.home.list.ListViewModel

class PasswordVMFactory private constructor(private val repository: PasswordRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddPasswordViewModel::class.java) -> {
                AddPasswordViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            else -> throw Throwable("Unknown view model class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: PasswordVMFactory? = null

        fun getInstance(context: Context): PasswordVMFactory =
            instance ?: synchronized(this) {
                instance ?: PasswordVMFactory(PasswordRepository.getInstance(context))
            }
    }
}