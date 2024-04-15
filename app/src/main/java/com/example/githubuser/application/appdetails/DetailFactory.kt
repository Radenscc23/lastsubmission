package com.example.githubuser.application.appdetails
import androidx.lifecycle.ViewModelProvider
import android.app.Application
import androidx.lifecycle.ViewModel


class DetailFactory(private val user: String, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserModel::class.java)) {
            return DetailUserModel(user,application) as T }
        throw IllegalArgumentException("Class tidak diketahui") } }