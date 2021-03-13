package com.challenge.movie.commands

import com.challenge.movie.dataModel.MovieInfo


sealed class SearchCommand {
    class SubmitData(val datMap:Map<Int, List<MovieInfo>>) : SearchCommand()
}