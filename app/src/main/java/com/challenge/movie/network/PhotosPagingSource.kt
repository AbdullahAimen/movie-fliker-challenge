package com.challenge.movie.network

import androidx.paging.PagingSource
import com.challenge.movie.dataModel.Photo
import retrofit2.HttpException
import java.io.IOException

class PhotosPagingSource(
    private val mApiHelper: ApiHelper,
    private val movieTitle: String
) : PagingSource<String, Photo>() {
    var pageIndex = 1
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Photo> {
        return try {
            val data = mApiHelper.getMoviePhotos(
                pageIndex = params.key?.toInt() ?: pageIndex,
                movieName = movieTitle
            )
            if (data.photos.photo.size == ApiHelper.PAGE_SIZE)
                pageIndex += 1
            LoadResult.Page(
                data = data.photos.photo,
                prevKey = null, //only load forward page
                nextKey = if (data.photos.photo.size < ApiHelper.PAGE_SIZE) null else "$pageIndex"
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}