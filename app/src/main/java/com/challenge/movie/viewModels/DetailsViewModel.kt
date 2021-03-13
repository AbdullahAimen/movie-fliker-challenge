package com.challenge.movie.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.challenge.movie.network.ApiHelper
import com.challenge.movie.network.NetworkHelper
import com.challenge.movie.network.PhotosPagingSource

class DetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val apiInstance = NetworkHelper.getInstance()

    fun loadMoviePhotos(title: String) = Pager(
        PagingConfig(
            pageSize = ApiHelper.PAGE_SIZE
        )
    ) {
        PhotosPagingSource(apiInstance, title)
    }.flow.cachedIn(viewModelScope)
}