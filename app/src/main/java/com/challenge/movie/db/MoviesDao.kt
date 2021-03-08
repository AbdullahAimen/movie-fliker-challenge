package com.challenge.movie.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.challenge.movie.dataModel.MovieInfo

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieInfo")
    fun allMovies(): PagingSource<Int, MovieInfo>

    @Query("SELECT * FROM MovieInfo WHERE title LIKE '%' || :tempTitle || '%' ORDER BY rating")
    fun searchForMovie(tempTitle: String): List<MovieInfo>

    @Insert
    fun insert(items: List<MovieInfo>)

    @Query("DELETE FROM MovieInfo")
    fun deleteAll()
}