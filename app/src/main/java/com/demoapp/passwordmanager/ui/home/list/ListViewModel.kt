package com.demoapp.passwordmanager.ui.home.list

import androidx.lifecycle.*
import com.demoapp.passwordmanager.core.data.PasswordRepository
import com.demoapp.passwordmanager.core.data.password.PasswordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListViewModel(private val repository: PasswordRepository): ViewModel() {

    private val _passwordFLow = MutableStateFlow<List<PasswordEntity>>(emptyList())
    val passwordFlow: StateFlow<List<PasswordEntity>> = _passwordFLow

    fun getAllPassword(id: Int): Flow<List<PasswordEntity>> {
        return repository.getAllPassword(id)
    }

    suspend fun getAllPasswordStateFlow(id: Int): StateFlow<List<PasswordEntity>> {
        return repository.getPasswordStateFlow(id)
    }

    fun deleteCredential(item: PasswordEntity) = viewModelScope.launch {
        repository.deletePass(item)
    }

    // for using live data:
//    fun getAllPassword(id: Int): LiveData<List<PasswordEntity>> {
//        return repository.getAllPassword(id).asLiveData()
//    }
}