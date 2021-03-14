package com.challenge.movie

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.challenge.movie.dataModel.MovieInfo
import com.challenge.movie.dataModel.MovieObj
import com.challenge.movie.db.readAssetsFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import timber.log.Timber

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var mMovieObj: MovieObj
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.challenge.movie", appContext.packageName)
    }

    @Test
    fun readAppAsset() {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val fileContent = ctx.assets.readAssetsFile("movies.json")
        mMovieObj =
            Gson().fromJson(fileContent, object : TypeToken<MovieObj>() {}.type)
        val isEmptyList = mMovieObj.movies.isNullOrEmpty()
        Timber.d("app_size ${mMovieObj.movies}")
        assertEquals(false, isEmptyList)
        val isNotFound = mMovieObj.movies.none { it.title == "12 Rounds" }
        assertEquals(false, isNotFound)
    }
}