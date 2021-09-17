package com.san.moviedbclientmvc.common.navdrawer;

import android.widget.FrameLayout;

import com.san.moviedbclientmvc.common.views.ObservableViewMvc;

public interface NavDrawerViewMvc extends ObservableViewMvc<NavDrawerViewMvc.Listener> {

    interface Listener {
        void onMovieListItemClicked();
    }

    FrameLayout getFragmentFrame();

    boolean isDrawerOpen();
    void openDrawer();
    void closeDrawer();

}
