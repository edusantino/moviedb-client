package com.san.moviedbclientmvc.common.controllers;

import androidx.fragment.app.Fragment;

import com.san.moviedbclientmvc.common.di.ControllerCompositionRoot;
import com.san.moviedbclientmvc.ui.main.MainActivity;

public class BaseFragment extends Fragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((MainActivity) requireActivity()).getActivityCompositionRoot()
            );
        }
        return mControllerCompositionRoot;
    }
}
