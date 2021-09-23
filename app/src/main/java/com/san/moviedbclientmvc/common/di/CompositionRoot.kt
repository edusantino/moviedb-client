package com.san.moviedbclientmvc.common.di

import android.provider.SyncStateContract
import com.san.moviedbclientmvc.common.dialogs.DialogsEventBus
import com.san.moviedbclientmvc.common.permissions.Constants
import com.san.moviedbclientmvc.networking.MoviedbApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CompositionRoot {

    private var mRetrofit: Retrofit? = null
    private var mDialogsEventBus: DialogsEventBus? = null

    private fun getRetrofit(): Retrofit? {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return mRetrofit
    }

    fun getMovieApiService(): MoviedbApiService = getRetrofit()!!.create(MoviedbApiService::class.java)

    fun getDialogsEventBus(): DialogsEventBus? {
        if (mDialogsEventBus == null) {
            mDialogsEventBus = DialogsEventBus()
        }
        return mDialogsEventBus
    }
}