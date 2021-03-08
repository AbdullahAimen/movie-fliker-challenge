package com.challenge.movie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.challenge.movie.adapters.MovieAdapter
import com.challenge.movie.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val mMainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //creating adapter
        val adapter = MovieAdapter()
        main_recycler_movies.adapter = adapter

        //subscribe adapter to viewModel
        lifecycleScope.launch {
            mMainViewModel.allMovies.collectLatest { adapter.submitData(it) }
        }
    }
}