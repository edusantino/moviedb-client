package com.san.moviedbclientmvc.ui.movieslist.movieslistitem

import com.san.moviedbclientmvc.common.views.ObservableViewMvc
import com.san.moviedbclientmvc.networking.model.Movie

interface MoviesListItemViewContract : ObservableViewMvc<MoviesListItemViewContract.Listener?> {

    interface Listener {
        fun onMovieItemClicked(movie: Movie?)
    }

    fun bindMovie(movie: Movie?)
}