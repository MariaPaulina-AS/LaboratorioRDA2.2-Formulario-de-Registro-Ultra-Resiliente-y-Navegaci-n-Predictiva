package com.example.laboratorio1.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.laboratorio1.data.UserPreferences
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class FormViewModel(
    private val stateHandle: SavedStateHandle,
    private val userPrefs: UserPreferences
) : ViewModel() {

    var email by mutableStateOf(stateHandle.get<String>("email_key") ?: "")
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
        stateHandle["email_key"] = newEmail
    }

    val nameFromDisk = userPrefs.userName.asLiveData()

    var showSavedIcon by mutableStateOf(false)
        private set

    fun saveName(newName: String) {
        viewModelScope.launch {
            userPrefs.saveName(newName)
            showSavedIcon = true
            delay(1000)
            showSavedIcon = false
        }
    }
}