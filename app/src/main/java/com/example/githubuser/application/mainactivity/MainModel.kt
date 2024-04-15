package com.example.githubuser.application.mainactivity
import com.example.githubuser.api.ClickUser
import com.example.githubuser.repo.UserRepository
import com.example.githubuser.application.themes.darkmodesetting.ThemePreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainModel(private val userPreference: ThemePreferences) : ViewModel() {

    val userList: LiveData<ArrayList<ClickUser>?> = UserRepository._isUser
    val userSearch: LiveData<ArrayList<ClickUser>?> = UserRepository._isSearch
    fun getThemeSettings(): LiveData<Boolean> { return userPreference.getThemeSetting().asLiveData() }
    override fun onCleared() {
        super.onCleared()
        UserRepository.viewModelJob.cancel() }
    init {
        viewModelScope.launch{
            UserRepository.getListUser()
        }

    }

}
