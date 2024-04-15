package com.example.githubuser.application.sections.favoritesection
import androidx.lifecycle.ViewModelProvider
import android.app.Application
import androidx.lifecycle.ViewModel

class FavFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var _isInstance: FavFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavFactory {
            if (_isInstance == null) {
                synchronized(FavFactory::class.java) {
                    _isInstance = FavFactory(application) } }
            return _isInstance as FavFactory
        } }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavUserModel::class.java)) {
            return FavUserModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name} ")
    }

}