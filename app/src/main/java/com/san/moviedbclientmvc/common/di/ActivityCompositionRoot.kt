package com.san.moviedbclientmvc.common.di

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.san.moviedbclientmvc.common.dialogs.DialogsEventBus

class ActivityCompositionRoot (compositionRoot: CompositionRoot,
                                      activity: FragmentActivity) {

    private val mCompositionRoot: CompositionRoot = compositionRoot
    private val mActivity: FragmentActivity = activity

    fun getActivity(): FragmentActivity = mActivity

    fun getMoviedbApiService() = mCompositionRoot.getMovieApiService()

    fun getDialogsEventBus(): DialogsEventBus? = mCompositionRoot.getDialogsEventBus()
}