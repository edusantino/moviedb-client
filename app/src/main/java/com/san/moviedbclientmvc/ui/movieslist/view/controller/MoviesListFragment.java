package com.san.moviedbclientmvc.ui.movieslist.view.controller;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.san.moviedbclientmvc.common.controllers.BaseFragment;

public class MoviesListFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new MoviesListFragment();
    }
    private static final String SAVED_STATE_CONTROLLER = "SAVED_STATE_CONTROLLER";
    private MoviesListController mMoviesListController;
    private static final String LIST_STATE_KEY = "LIST_STATE_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MoviesListViewContract viewMvc = getCompositionRoot().getViewMvcFactory().getMoviesListViewContract(container);

        mMoviesListController = getCompositionRoot().getMoviesListController();
        if (savedInstanceState != null) {
            restoreControllerState(savedInstanceState);
        }
        mMoviesListController.bindView(viewMvc);

        return viewMvc.getRootView();
    }

    private void restoreControllerState(Bundle savedInstanceState) {
        mMoviesListController.restoreSavedState(
                (MoviesListController.SavedState)
                        savedInstanceState.getSerializable(SAVED_STATE_CONTROLLER)
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        mMoviesListController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMoviesListController.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE_CONTROLLER, mMoviesListController.getSavedState());
        outState.putParcelable(LIST_STATE_KEY, mMoviesListController.getmParcelable());
    }
    
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }
}
