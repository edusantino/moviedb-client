package com.san.moviedbclientmvc.common.factory

import android.view.LayoutInflater
import android.view.ViewGroup

import com.san.moviedbclientmvc.common.dialogs.promptdialog.PromptViewMvcImpl
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerHelper
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerViewMvc
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerViewMvcImpl
import com.san.moviedbclientmvc.common.toolbar.ToolbarViewMvc
import com.san.moviedbclientmvc.ui.moviedetails.view.MovieDetailsViewContract
import com.san.moviedbclientmvc.ui.moviedetails.view.MovieDetailsView
import com.san.moviedbclientmvc.ui.movieslist.view.MoviesListView
import com.san.moviedbclientmvc.ui.movieslist.movieslistitem.MoviesListItemViewContract
import com.san.moviedbclientmvc.ui.movieslist.movieslistitem.MoviesListItemView
import com.san.moviedbclientmvc.ui.movieslist.view.controller.MoviesListViewContract


class ViewMvcFactory (layoutInflater: LayoutInflater,
                      navDrawerHelper: NavDrawerHelper) {

    private val mLayoutInflater: LayoutInflater = layoutInflater
    private val mNavDrawerHelper: NavDrawerHelper = navDrawerHelper

    fun getMoviesListViewContract(parent: ViewGroup?): MoviesListViewContract = MoviesListView(mLayoutInflater, parent, mNavDrawerHelper, this)

    fun getQuestionsListItemViewMvc(parent: ViewGroup?): MoviesListItemViewContract = MoviesListItemView(mLayoutInflater, parent)

    fun getMovieDetailsViewMvc(parent: ViewGroup?): MovieDetailsViewContract = MovieDetailsView(mLayoutInflater, parent, this)

    fun getToolbarViewMvc(parent: ViewGroup?): ToolbarViewMvc = ToolbarViewMvc(mLayoutInflater, parent)

    fun getNavDrawerViewMvc(parent: ViewGroup?): NavDrawerViewMvc = NavDrawerViewMvcImpl(mLayoutInflater, parent)

    fun getPromptViewMvc(parent: ViewGroup?): PromptViewMvcImpl = PromptViewMvcImpl(mLayoutInflater, parent)
}