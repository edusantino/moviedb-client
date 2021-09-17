package com.san.moviedbclientmvc.common.di;

import androidx.fragment.app.FragmentActivity;

import com.san.moviedbclientmvc.networking.MoviedbApiService;
import com.san.moviedbclientmvc.common.dialogs.DialogsEventBus;

public class ActivityCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;

    public ActivityCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    public FragmentActivity getActivity() {
        return mActivity;
    }

    public MoviedbApiService getMoviedbApiService() {
        return mCompositionRoot.getMovieApiService();
    }

    public DialogsEventBus getDialogsEventBus() {
        return mCompositionRoot.getDialogsEventBus();
    }
}
