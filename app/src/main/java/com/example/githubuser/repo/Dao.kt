package com.example.githubuser.repo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.lifecycle.LiveData


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: Favorite)

    @Update
    fun update(fav: Favorite)

    @Delete
    fun delete(fav: Favorite)

    @Query("SELECT  * from favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT  * from favorite WHERE id = :id")
    fun getUserFavoriteById(id: Int): LiveData<List<Favorite>>
}