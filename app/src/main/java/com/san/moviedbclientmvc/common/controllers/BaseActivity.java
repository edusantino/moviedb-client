package com.san.moviedbclientmvc.common.controllers;

import androidx.appcompat.app.AppCompatActivity;

import com.san.moviedbclientmvc.common.CustomApplication;
import com.san.moviedbclientmvc.common.di.ActivityCompositionRoot;
import com.san.moviedbclientmvc.common.di.ControllerCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private ActivityCompositionRoot mActivityCompositionRoot;
    private ControllerCompositionRoot mControllerCompositionRoot;

    public ActivityCompositionRoot getActivityCompositionRoot() {
        if (mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mActivityCompositionRoot;
    }

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(getActivityCompositionRoot());
        }
        return mControllerCompositionRoot;
    }

}
