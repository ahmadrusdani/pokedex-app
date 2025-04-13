package com.amd.pokedexapp.presentation.page.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amd.pokedexapp.data.local.datasource.DataPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataPreference: DataPreference
) : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _loginError = MutableSharedFlow<String>()
    val loginError: SharedFlow<String> = _loginError

    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (username.length > 5 && password.length > 5) {
                _loginSuccess.value = true
                dataPreference.saveLoginState(true)
                dataPreference.saveUserInfo(username, "$username@email.com")
            } else {
                _loginSuccess.value = false
                _loginError.emit("Username and password must be longer than 5 characters.")
            }
        }
    }
}