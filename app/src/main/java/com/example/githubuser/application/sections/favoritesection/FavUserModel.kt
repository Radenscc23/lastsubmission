package com.example.githubuser.application.sections.favoritesection
import com.example.githubuser.repo.Favorite
import com.example.githubuser.repo.SettingRepository
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class FavUserModel(application : Application) : ViewModel() { private val mFavRepository : SettingRepository = SettingRepository(application)
    fun getAllFavorites() : LiveData<List<Favorite>> = mFavRepository.getfavoriteUsers()
}