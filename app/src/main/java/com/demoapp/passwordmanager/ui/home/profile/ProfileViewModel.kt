package com.demoapp.passwordmanager.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.demoapp.passwordmanager.core.data.UserRepository
import com.demoapp.passwordmanager.core.data.user.UserEntity

class ProfileViewModel(private val repository: UserRepository): ViewModel() {

    private val _userId = MutableLiveData<Int>()

    fun setUserId(id: Int) {
        _userId.value = id
    }

    val profile: LiveData<UserEntity> = _userId.switchMap { repository.getProfile(it) }
}