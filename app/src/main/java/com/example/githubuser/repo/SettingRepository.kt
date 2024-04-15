package com.example.githubuser.repo
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.app.Application
import androidx.lifecycle.LiveData


class SettingRepository(application: Application) {
    private val favDao: Dao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDB.getDatabase(application)
        favDao = db.favDao()
    }

    fun getfavoriteUsers(): LiveData<List<Favorite>> = favDao.getAllFavorite()
    fun getuserById(id: Int): LiveData<List<Favorite>> =
        favDao.getUserFavoriteById(id)

    fun insert(fav: Favorite) {
        executorService.execute { favDao.insert(fav) }
    }

    fun delete(fav: Favorite) {
        executorService.execute { favDao.delete(fav) }
    }
}
