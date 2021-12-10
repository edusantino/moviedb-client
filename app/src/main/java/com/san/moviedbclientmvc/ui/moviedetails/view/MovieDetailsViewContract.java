package com.san.moviedbclientmvc.ui.moviedetails.view;

import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.common.views.ObservableViewMvc;

public interface MovieDetailsViewContract extends ObservableViewMvc<MovieDetailsViewContract.Listener> {

    public interface Listener {
        void onNavigateUpClicked();
    }

    void bindMovie(Movie movie);
    void showProgressIndication();
    void hideProgressIndication();
    void initViews();
}
