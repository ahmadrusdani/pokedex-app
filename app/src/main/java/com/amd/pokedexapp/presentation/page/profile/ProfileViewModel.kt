package com.amd.pokedexapp.presentation.page.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amd.pokedexapp.data.local.datasource.DataPreference
import com.amd.pokedexapp.domain.model.user.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataPreference: DataPreference
) : ViewModel() {

    val userInfo: StateFlow<UserModel> = dataPreference.getUserInfo()
        .stateIn(viewModelScope, SharingStarted.Lazily, UserModel("", ""))

}