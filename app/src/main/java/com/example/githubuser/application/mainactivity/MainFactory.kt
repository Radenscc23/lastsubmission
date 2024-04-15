package com.example.githubuser.application.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.application.themes.darkmodesetting.ThemePreferences

class MainFactory(private val pref: ThemePreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainModel::class.java)) {
            return MainModel(pref) as T }
        throw IllegalArgumentException("Class tidak diketahui: " + modelClass.name)
    }
}