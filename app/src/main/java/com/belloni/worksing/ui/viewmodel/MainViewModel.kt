package com.belloni.worksing.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belloni.worksing.data.model.User
import com.belloni.worksing.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Sealed interface to represent UI state
sealed interface UserUiState {
    data class Success(val users: List<User>) : UserUiState
    data class Error(val message: String) : UserUiState
    object Loading : UserUiState
}

class MainViewModel : ViewModel() {

    // Private mutable state flow
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    // Public immutable state flow for the UI to observe
    val uiState: StateFlow<UserUiState> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val response = RetrofitClient.instance.getUsers()
                _uiState.value = UserUiState.Success(response.data)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}