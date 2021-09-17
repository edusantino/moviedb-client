package com.san.moviedbclientmvc.common.screensnavigator;

import com.san.moviedbclientmvc.common.fragmentframehelper.FragmentFrameHelper;
import com.san.moviedbclientmvc.ui.moviedetails.view.MovieDetailsFragment;
import com.san.moviedbclientmvc.ui.movieslist.view.controller.MoviesListFragment;

public class ScreensNavigator {

    private FragmentFrameHelper mFragmentFrameHelper;

    public ScreensNavigator(FragmentFrameHelper fragmentFrameHelper) {
        mFragmentFrameHelper = fragmentFrameHelper;
    }

    public void toMovieDetails(Integer movieId) {
        mFragmentFrameHelper.replaceFragment(MovieDetailsFragment.newInstance(movieId));
    }

    public void toMoviesList() {
        mFragmentFrameHelper.replaceFragmentAndClearBackstack(MoviesListFragment.newInstance());
    }

    public void navigateUp() {
        mFragmentFrameHelper.navigateUp();
    }
}
