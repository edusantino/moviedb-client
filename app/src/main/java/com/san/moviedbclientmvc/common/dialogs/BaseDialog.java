package com.san.moviedbclientmvc.common.dialogs;

import androidx.fragment.app.DialogFragment;

import com.san.moviedbclientmvc.common.di.ControllerCompositionRoot;
import com.san.moviedbclientmvc.ui.main.MainActivity;

public abstract class BaseDialog extends DialogFragment {

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
