package com.challenge.movie.dataModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieInfo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    val title: String,
    val year: Int
)