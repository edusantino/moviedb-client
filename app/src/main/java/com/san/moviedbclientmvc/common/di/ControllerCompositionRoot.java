package com.san.moviedbclientmvc.common.di;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.san.moviedbclientmvc.ui.movieslist.usecase.FetchLastActiveMoviesUseCase;
import com.san.moviedbclientmvc.ui.moviedetails.usecase.FetchMovieDetailsUseCase;
import com.san.moviedbclientmvc.networking.MoviedbApiService;
import com.san.moviedbclientmvc.common.factory.ViewMvcFactory;
import com.san.moviedbclientmvc.common.controllers.BackPressDispatcher;
import com.san.moviedbclientmvc.common.dialogs.DialogsEventBus;
import com.san.moviedbclientmvc.common.dialogs.DialogsManager;
import com.san.moviedbclientmvc.common.fragmentframehelper.FragmentFrameHelper;
import com.san.moviedbclientmvc.common.fragmentframehelper.FragmentFrameWrapper;
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerHelper;
import com.san.moviedbclientmvc.common.screensnavigator.ScreensNavigator;
import com.san.moviedbclientmvc.common.toastshelper.ToastsHelper;
import com.san.moviedbclientmvc.ui.movieslist.view.controller.MoviesListController;

public class ControllerCompositionRoot {

    private final ActivityCompositionRoot mActivityCompositionRoot;

    public ControllerCompositionRoot(ActivityCompositionRoot activityCompositionRoot) {
        mActivityCompositionRoot = activityCompositionRoot;
    }

    private FragmentActivity getActivity() {
        return mActivityCompositionRoot.getActivity();
    }

    private Context getContext() {
        return getActivity();
    }

    private FragmentManager getFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    private MoviedbApiService getMoviedbApiService() {
        return mActivityCompositionRoot.getMoviedbApiService();
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater(), getNavDrawerHelper());
    }

    private NavDrawerHelper getNavDrawerHelper() {
        return (NavDrawerHelper) getActivity();
    }

    public FetchMovieDetailsUseCase getFetchMovieDetailsUseCase() {
        return new FetchMovieDetailsUseCase(getMoviedbApiService());
    }

    public FetchLastActiveMoviesUseCase getFetchLastActiveMoviesUseCase() {
        return new FetchLastActiveMoviesUseCase(getMoviedbApiService());
    }

    public MoviesListController getMoviesListController() {
        return new MoviesListController(
                getFetchLastActiveMoviesUseCase(),
                getScreensNavigator(),
                getDialogsManager(),
                getDialogsEventBus()
        );
    }

    public ToastsHelper getToastsHelper() {
        return new ToastsHelper(getContext());
    }

    public ScreensNavigator getScreensNavigator() {
        return new ScreensNavigator(getFragmentFrameHelper());
    }

    private FragmentFrameHelper getFragmentFrameHelper() {
        return new FragmentFrameHelper(getActivity(), getFragmentFrameWrapper(), getFragmentManager());
    }

    private FragmentFrameWrapper getFragmentFrameWrapper() {
        return (FragmentFrameWrapper) getActivity();
    }

    public BackPressDispatcher getBackPressDispatcher() {
        return (BackPressDispatcher) getActivity();
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(getContext(), getFragmentManager());
    }

    public DialogsEventBus getDialogsEventBus() {
        return mActivityCompositionRoot.getDialogsEventBus();
    }
}
