package com.challenge.movie.network

import com.challenge.movie.dataModel.FlickerResponse
import com.challenge.movie.dataModel.Photo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

interface ApiHelper {
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    suspend fun getMoviePhotos(
        @Query("per_page") per_page: Int? = PAGE_SIZE,
        @Query("page") pageIndex: Int? = 1,
        @Query("api_key") apiKey: String? = "13fa6e43c129d260a385b7d2debae378",
        @Query("text") movieName: String?
    ): FlickerResponse

    companion object {
        const val BASE_URL = "https://api.flickr.com/"
        const val PAGE_SIZE = 10
    }
}

class NetworkHelper {
    companion object {
        private var instance: ApiHelper? = null
        fun getInstance(): ApiHelper {
            if (instance == null) {
                instance = provideRetrofitService(provideRetrofit())
            }
            return instance!!
        }
    }
}

fun provideRetrofitService(retrofit: Retrofit): ApiHelper =
    retrofit.create(ApiHelper::class.java)

fun provideRetrofit(): Retrofit {
    val logger =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.d("API: $it") })
    logger.level = HttpLoggingInterceptor.Level.BASIC

    val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()
    return Retrofit.Builder()
        .baseUrl(ApiHelper.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getImgUrl(photoInfo: Photo) =
    "https://farm${photoInfo.farm}.static.flickr.com/${photoInfo.server}/${photoInfo.id}_${photoInfo.secret}.jpg"
