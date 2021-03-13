package com.challenge.movie.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.challenge.movie.commands.Event
import com.challenge.movie.commands.SearchCommand
import com.challenge.movie.db.MoviesDb
import com.challenge.movie.db.ioThread
import timber.log.Timber

class MainViewModel(app: Application) : AndroidViewModel(app) {
    val mSearchCommand: MutableLiveData<Event<SearchCommand>> = MutableLiveData()
    private val dao = MoviesDb.get(app).moviesDao()
    val allMovies = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            maxSize = 60
        )
    ) {
        dao.allMovies()
    }.flow

    fun doSearch(tempTitle: String) {
        ioThread {
            val movies = dao.searchForMovie(tempTitle)
            val moviesGroupedByYear = movies.groupBy { it.year }
            Timber.i("listCount: ${movies.size}")
            mSearchCommand.postValue(Event(SearchCommand.SubmitData(moviesGroupedByYear)))
        }
    }
}