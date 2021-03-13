package com.challenge.movie.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.challenge.movie.R
import com.challenge.movie.dataModel.MovieInfo
import com.challenge.movie.views.DetailsActivity

class MoviesViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
) {

    private val title = itemView.findViewById<TextView>(R.id.movieItem_tv_title)
    private val genres = itemView.findViewById<TextView>(R.id.movieItem_tv_genres)
    private val rate = itemView.findViewById<TextView>(R.id.movieItem_tv_rate)
    private val year = itemView.findViewById<TextView>(R.id.movieItem_tv_year)
    private val cast = itemView.findViewById<TextView>(R.id.movieItem_tv_cast)
    fun bindTo(item: MovieInfo?) {
        title.text = item?.title
        rate.text = "${item?.rating}"
        year.text = "${item?.year}"
        genres.text = item?.genres.toString()
        cast.text = item?.cast.toString()
        itemView.findViewById<View>(R.id.movieItem_view_root).setOnClickListener {
            val intent = Intent(parent.context, DetailsActivity::class.java)
            val mBundle = Bundle()
            mBundle.putParcelable("MOVE_DETAILS", item)
            intent.putExtras(mBundle)
            parent.context.startActivity(intent)
        }
    }
}

class MovieAdapter : PagingDataAdapter<MovieInfo, MoviesViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MovieInfo>() {
            override fun areItemsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean =
                oldItem == newItem
        }
    }
}