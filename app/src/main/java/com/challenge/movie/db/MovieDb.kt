package com.challenge.movie.db

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.challenge.movie.dataModel.MovieInfo
import com.challenge.movie.dataModel.MovieObj
import com.challenge.movie.dataModel.StringsTypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.util.concurrent.Executors
@Database(entities = [MovieInfo::class], version = 1)
@TypeConverters(StringsTypeConverter::class)
abstract class MoviesDb : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        private var instance: MoviesDb? = null

        @Synchronized
        fun get(context: Context): MoviesDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDb::class.java, "MovieDatabase"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            fillInDb(context.applicationContext)
                        }
                    }).build()
            }
            return instance!!
        }

        /**
         * fill database with list of movies
         */
        private fun fillInDb(context: Context) {
            // inserts in Room are executed on the current thread, so we insert in the background
            ioThread {
                val fileContent =
                    context.assets.readAssetsFile("movies.json")
                val mMovieObj: MovieObj =
                    Gson().fromJson(fileContent, object : TypeToken<MovieObj>() {}.type)
                Timber.d("listCount ${mMovieObj.movies.size}")
                get(context).moviesDao().insert(mMovieObj.movies)
            }
        }
    }
}

fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }
