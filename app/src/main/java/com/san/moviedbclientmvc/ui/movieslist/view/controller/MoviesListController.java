package com.san.moviedbclientmvc.ui.movieslist.view.controller;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.moviedbclientmvc.common.dialogs.DialogsEventBus;
import com.san.moviedbclientmvc.common.dialogs.DialogsManager;
import com.san.moviedbclientmvc.common.screensnavigator.ScreensNavigator;
import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.ui.movieslist.usecase.FetchLastActiveMoviesUseCase;
import com.san.moviedbclientmvc.common.dialogs.promptdialog.PromptDialogEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoviesListController implements
        MoviesListViewContract.Listener,
        FetchLastActiveMoviesUseCase.Listener,
        DialogsEventBus.Listener {

    private enum ScreenState {
        IDLE, FETCHING_MOVIES, MOVIES_LIST_SHOWN, NETWORK_ERROR
    }

    private final FetchLastActiveMoviesUseCase mFetchLastActiveMoviesUseCase;
    private final ScreensNavigator mScreensNavigator;
    private final DialogsManager mDialogsManager;
    private final DialogsEventBus mDialogsEventBus;

    private MoviesListViewContract mViewMvc;
    private Parcelable mParcelable;

    private ScreenState mScreenState = ScreenState.IDLE;

    public MoviesListController(FetchLastActiveMoviesUseCase fetchLastActiveMoviesUseCase,
                                ScreensNavigator screensNavigator,
                                DialogsManager dialogsManager,
                                DialogsEventBus dialogsEventBus) {
        mFetchLastActiveMoviesUseCase = fetchLastActiveMoviesUseCase;
        mScreensNavigator = screensNavigator;
        mDialogsManager = dialogsManager;
        mDialogsEventBus = dialogsEventBus;
    }

    public void bindView(MoviesListViewContract viewMvc) {
        mViewMvc = viewMvc;
    }

    public SavedState getSavedState() {
        return new SavedState(mScreenState);
    }

    public Parcelable getmParcelable() { return mParcelable; }

    public void restoreSavedState(SavedState savedState) {
        mScreenState = savedState.mScreenState;
    }

    public void onStart() {
        mViewMvc.registerListener(this);
        mFetchLastActiveMoviesUseCase.registerListener(this);
        mDialogsEventBus.registerListener(this);

        if (mScreenState != ScreenState.NETWORK_ERROR) {
            fetchMoviesAndNotify();
        }
    }

    public void onStop() {
        mViewMvc.unregisterListener(this);
        mFetchLastActiveMoviesUseCase.unregisterListener(this);
        mDialogsEventBus.unregisterListener(this);
    }

    private void fetchMoviesAndNotify() {
        mScreenState = ScreenState.FETCHING_MOVIES;
        mViewMvc.showProgressIndication();
        mFetchLastActiveMoviesUseCase.fetchLastActiveMoviesAndNotify();
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        mScreensNavigator.toMovieDetails(movie.getId());
    }

    @NonNull
    @Override
    public Parcelable saveOnBackPressed(@NonNull Parcelable parcelable) {
        return parcelable;
    }

    @Override
    public void onLastActiveQuestionsFetched(List<Movie> movies) {
        mScreenState = ScreenState.MOVIES_LIST_SHOWN;
        mViewMvc.hideProgressIndication();
        mViewMvc.bindMovies(movies);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        mScreenState = ScreenState.NETWORK_ERROR;
        mViewMvc.hideProgressIndication();
        mDialogsManager.showUseCaseErrorDialog(null);
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof PromptDialogEvent) {
            switch (((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    fetchMoviesAndNotify();
                    break;
                case NEGATIVE:
                    mScreenState = ScreenState.IDLE;
                    break;
            }
        }
    }

    public static class SavedState implements Serializable {
        private final ScreenState mScreenState;

        public SavedState(ScreenState screenState) {
            mScreenState = screenState;
        }
    }
}
