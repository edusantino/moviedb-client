package com.san.moviedbclientmvc.ui.movieslist.view.controller;

import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.common.views.ObservableViewMvc;

import java.util.List;

public interface MoviesListViewContract extends ObservableViewMvc<MoviesListViewContract.Listener> {

    public interface Listener {
        void onMovieItemClicked(Movie movie);
    }

    void bindMovies(List<Movie> movies);

    void showProgressIndication();

    void hideProgressIndication();

}
