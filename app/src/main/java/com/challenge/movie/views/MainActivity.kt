package com.challenge.movie.views

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.challenge.movie.BuildConfig
import com.challenge.movie.R
import com.challenge.movie.adapters.ExpandableListAdapter
import com.challenge.movie.adapters.MovieAdapter
import com.challenge.movie.commands.SearchCommand
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

        main_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    main_expandableList.visibility = View.GONE
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    main_expandableList.visibility = View.GONE
                    return true
                }
                mMainViewModel.doSearch(newText)
                return false
            }

        })
        mMainViewModel.mSearchCommand.observe(this, Observer {
            it.getContentIfNotHandled()?.let { it ->
                when (it) {
                    is SearchCommand.SubmitData -> {
                        val mExpandableListAdapter = ExpandableListAdapter(this, it.datMap)
                        main_expandableList.setAdapter(mExpandableListAdapter)
                        if (it.datMap.isNotEmpty())
                            main_expandableList.visibility = View.VISIBLE
                        else
                            main_expandableList.visibility = View.GONE

                    }
                }
            }
        })
    }
}