package com.challenge.movie.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.challenge.movie.db.MoviesDb
import com.challenge.movie.db.ioThread
import timber.log.Timber

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = MoviesDb.get(app).moviesDao()

    fun doSearch(tempTitle: String) {
        ioThread {
            val movies = dao.searchForMovie(tempTitle)
            Timber.i("listCount: ${movies.size}")
        }
    }
}