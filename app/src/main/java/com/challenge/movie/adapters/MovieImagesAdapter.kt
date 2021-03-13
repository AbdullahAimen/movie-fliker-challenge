package com.challenge.movie.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.movie.R
import com.challenge.movie.dataModel.Photo
import com.challenge.movie.network.getImgUrl
import timber.log.Timber

class MovieImagesViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie_image, parent, false)
) {

    private val img = itemView.findViewById<ImageView>(R.id.itemMovie_img)
    fun bindTo(item: Photo?) {
        if (item != null) {
            val imgUrl = getImgUrl(item!!)
            Timber.d("IMAGE_URL: $imgUrl" )
            Glide
                .with(parent.context)
                .load(imgUrl)
                .fitCenter()
                .into(img)
        }
    }
}

class MovieImagesAdapter : PagingDataAdapter<Photo, MovieImagesViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: MovieImagesViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImagesViewHolder =
        MovieImagesViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id
        }
    }
}