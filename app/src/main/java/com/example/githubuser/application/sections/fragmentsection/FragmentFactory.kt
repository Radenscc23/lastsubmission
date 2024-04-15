package com.example.githubuser.application.sections.fragmentsection
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel

class FragmentFactory(private val username: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentModel::class.java)) {
            return FragmentModel(username) as T
        }
        throw IllegalArgumentException("Class tidak diketahui")
    } }