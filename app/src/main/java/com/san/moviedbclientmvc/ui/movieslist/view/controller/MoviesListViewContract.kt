package com.san.moviedbclientmvc.ui.movieslist.view.controller

import com.san.moviedbclientmvc.common.views.ObservableViewMvc
import com.san.moviedbclientmvc.networking.model.Movie

interface MoviesListViewContract: ObservableViewMvc<MoviesListViewContract.Listener> {

    interface Listener {
        fun onMovieItemClicked(movie: Movie)
    }

    fun bindMovies(movies: List<Movie>)
    fun showProgressIndication()
    fun hideProgressIndication()
}