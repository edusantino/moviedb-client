package com.san.moviedbclientmvc.common.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.san.moviedbclientmvc.common.dialogs.promptdialog.PromptViewMvc;
import com.san.moviedbclientmvc.common.dialogs.promptdialog.PromptViewMvcImpl;
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerHelper;
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerViewMvc;
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerViewMvcImpl;
import com.san.moviedbclientmvc.common.toolbar.ToolbarViewMvc;
import com.san.moviedbclientmvc.ui.moviedetails.view.MovieDetailsViewContract;
import com.san.moviedbclientmvc.ui.moviedetails.view.MovieDetailsView;
import com.san.moviedbclientmvc.ui.movieslist.view.controller.MoviesListViewContract;
import com.san.moviedbclientmvc.ui.movieslist.view.MoviesListView;
import com.san.moviedbclientmvc.ui.movieslist.movieslistitem.MoviesListItemViewContract;
import com.san.moviedbclientmvc.ui.movieslist.movieslistitem.MoviesListItemView;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private final NavDrawerHelper mNavDrawerHelper;

    public ViewMvcFactory(LayoutInflater layoutInflater, NavDrawerHelper navDrawerHelper) {
        mLayoutInflater = layoutInflater;
        mNavDrawerHelper = navDrawerHelper;
    }

    public MoviesListViewContract getMoviesListViewContract(@Nullable ViewGroup parent) {
        return new MoviesListView(mLayoutInflater, parent, mNavDrawerHelper, this);
    }

    public MoviesListItemViewContract getQuestionsListItemViewMvc(@Nullable ViewGroup parent) {
        return new MoviesListItemView(mLayoutInflater, parent);
    }

    public MovieDetailsViewContract getMovieDetailsViewMvc(@Nullable ViewGroup parent) {
        return new MovieDetailsView(mLayoutInflater, parent, this);
    }

    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvc(mLayoutInflater, parent);
    }

    public NavDrawerViewMvc getNavDrawerViewMvc(@Nullable ViewGroup parent) {
        return new NavDrawerViewMvcImpl(mLayoutInflater, parent);
    }

    public PromptViewMvc getPromptViewMvc(@Nullable ViewGroup parent) {
        return new PromptViewMvcImpl(mLayoutInflater, parent);
    }
}
