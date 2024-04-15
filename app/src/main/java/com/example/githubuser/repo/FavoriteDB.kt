package com.example.githubuser.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun favDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDB? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDB {
            if (INSTANCE == null) {
                synchronized(FavoriteDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDB::class.java, "Favorite_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteDB
        }
    }

}