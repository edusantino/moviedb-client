package com.san.moviedbclientmvc.ui.moviedetails.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.san.moviedbclientmvc.ui.moviedetails.usecase.FetchMovieDetailsUseCase;
import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.common.controllers.BaseFragment;
import com.san.moviedbclientmvc.common.dialogs.DialogsEventBus;
import com.san.moviedbclientmvc.common.dialogs.DialogsManager;
import com.san.moviedbclientmvc.common.dialogs.promptdialog.PromptDialogEvent;
import com.san.moviedbclientmvc.common.screensnavigator.ScreensNavigator;

public class MovieDetailsFragment extends BaseFragment implements
        FetchMovieDetailsUseCase.Listener,
        MovieDetailsViewContract.Listener,
        DialogsEventBus.Listener {

    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";
    private static final String DIALOG_ID_NETWORK_ERROR = "DIALOG_ID_NETWORK_ERROR";
    private static final String SAVED_STATE_SCREEN_STATE = "SAVED_STATE_SCREEN_STATE";
    public static final int REQUEST_CODE = 1001;

    public static MovieDetailsFragment newInstance(Integer movieId) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_ID, movieId);
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private enum ScreenState {
        IDLE, MOVIE_DETAILS_SHOWN, NETWORK_ERROR
    }

    private FetchMovieDetailsUseCase mFetchMovieDetailsUseCase;
    private ScreensNavigator mScreensNavigator;
    private DialogsManager mDialogsManager;
    private DialogsEventBus mDialogsEventBus;

    private MovieDetailsViewContract mViewMvc;

    private ScreenState mScreenState = ScreenState.IDLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mScreenState = (ScreenState) savedInstanceState.getSerializable(SAVED_STATE_SCREEN_STATE);
        }
        mFetchMovieDetailsUseCase = getCompositionRoot().getFetchMovieDetailsUseCase();
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mDialogsManager = getCompositionRoot().getDialogsManager();
        mDialogsEventBus = getCompositionRoot().getDialogsEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getCompositionRoot().getViewMvcFactory().getMovieDetailsViewMvc(container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mFetchMovieDetailsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mDialogsEventBus.registerListener(this);

        if (mScreenState != ScreenState.NETWORK_ERROR) {
            fetchMovieDetailsAndNotify();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchMovieDetailsUseCase.unregisterListener(this);
        mViewMvc.unregisterListener(this);
        mDialogsEventBus.unregisterListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_SCREEN_STATE, mScreenState);
    }

    private void fetchMovieDetailsAndNotify() {
        mViewMvc.showProgressIndication();
        mFetchMovieDetailsUseCase.fetchMovieDetailsAndNotify(getMovieId());
    }

    private int getMovieId() {
        return getArguments().getInt(ARG_MOVIE_ID);
    }

    @Override
    public void onMovieDetailsFetched(Movie movieDetails) {
        mScreenState = ScreenState.MOVIE_DETAILS_SHOWN;
        mViewMvc.hideProgressIndication();
        mViewMvc.bindMovie(movieDetails);
    }

    @Override
    public void onMovieDetailsFetchFailed() {
        mScreenState = ScreenState.NETWORK_ERROR;
        mViewMvc.hideProgressIndication();
        mDialogsManager.showUseCaseErrorDialog(DIALOG_ID_NETWORK_ERROR);
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof PromptDialogEvent) {
            switch (((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    mScreenState = ScreenState.IDLE;
                    fetchMovieDetailsAndNotify();
                    break;
                case NEGATIVE:
                    mScreenState = ScreenState.IDLE;
                    break;
            }
        }
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }

}
