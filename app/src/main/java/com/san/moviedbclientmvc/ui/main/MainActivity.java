package com.san.moviedbclientmvc.ui.main;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.san.moviedbclientmvc.common.controllers.BackPressDispatcher;
import com.san.moviedbclientmvc.common.controllers.BackPressedListener;
import com.san.moviedbclientmvc.common.controllers.BaseActivity;
import com.san.moviedbclientmvc.common.fragmentframehelper.FragmentFrameWrapper;
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerHelper;
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerViewMvc;
import com.san.moviedbclientmvc.common.screensnavigator.ScreensNavigator;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity implements
        BackPressDispatcher,
        FragmentFrameWrapper,
        NavDrawerViewMvc.Listener,
        NavDrawerHelper {

    private final Set<BackPressedListener> mBackPressedListeners = new HashSet<>();
    private ScreensNavigator mScreensNavigator;

    private NavDrawerViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getNavDrawerViewMvc(null);
        setContentView(mViewMvc.getRootView());

        if (savedInstanceState == null) {
            mScreensNavigator.toMoviesList();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onMovieListItemClicked() {
        mScreensNavigator.toMoviesList();
    }

    @Override
    public void registerListener(BackPressedListener listener) {
        mBackPressedListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressedListener listener) {
        mBackPressedListeners.remove(listener);
    }

    @Override
    public void onBackPressed() {
        boolean isBackPressConsumedByAnyListener = false;
        for (BackPressedListener listener : mBackPressedListeners) {
            if (listener.onBackPressed()) {
                isBackPressConsumedByAnyListener = true;
            }
        }
        if (isBackPressConsumedByAnyListener) {
            return;
        }

        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return mViewMvc.getFragmentFrame();
    }

    @Override
    public void openDrawer() {
        mViewMvc.openDrawer();
    }

    @Override
    public void closeDrawer() {
        mViewMvc.closeDrawer();
    }

    @Override
    public boolean isDrawerOpen() {
        return mViewMvc.isDrawerOpen();
    }
}
