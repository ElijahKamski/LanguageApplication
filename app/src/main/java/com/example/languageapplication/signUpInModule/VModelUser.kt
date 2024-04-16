package com.example.languageapplication.signUpInModule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VModelUser : ViewModel() {
    private val mutLiveDataUser = MutableLiveData<ModelUser>()

    fun setRegistrationData(user: ModelUser) {
        mutLiveDataUser.value = user
    }

    fun combineWithPassword(password: String): ModelUser? {
        val currentData = mutLiveDataUser.value
        return currentData?.copy(password = password)
    }
}