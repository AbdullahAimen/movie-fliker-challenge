package com.challenge.movie.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.movie.R
import com.challenge.movie.adapters.MovieImagesAdapter
import com.challenge.movie.dataModel.MovieInfo
import com.challenge.movie.viewModels.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    private val mDetailsViewModel by viewModels<DetailsViewModel>()
    private val mLayoutManager = GridLayoutManager(this, 2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val movieDetails: MovieInfo? = intent.getParcelableExtra("MOVE_DETAILS")
        details_tv_title.text = movieDetails?.title
        details_tv_genres.text = movieDetails?.genres.toString()
        details_tv_rate.text = "${movieDetails?.rating}"
        details_tv_year.text = "${movieDetails?.year}"
        details_tv_cast.text = movieDetails?.cast.toString()
        details_recycler_movies.layoutManager = mLayoutManager
        val mMovieImagesAdapter = MovieImagesAdapter()
        details_recycler_movies.adapter = mMovieImagesAdapter
        val searchTitle = movieDetails?.title ?: ""
        lifecycleScope.launch {
            mDetailsViewModel.loadMoviePhotos(searchTitle)
                .collectLatest { pagingData ->
                    mMovieImagesAdapter.submitData(pagingData)
                }
        }

    }
}