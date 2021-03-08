package com.challenge.movie

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.challenge.movie.viewModels.MainViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val mMainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.load).setOnClickListener {
            mMainViewModel.doSearch("X-Men")
        }
    }
}