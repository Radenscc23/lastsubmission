package com.example.githubuser.application.themes.darkmodesetting
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel


class ThemeFactory(private val themeSwitch: ThemePreferences) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeModel::class.java)) {
            return ThemeModel(themeSwitch) as T
        }
        throw IllegalArgumentException("Class tidak diketahui: " + modelClass.name)
    }
}