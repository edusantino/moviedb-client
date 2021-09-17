package com.san.moviedbclientmvc.common.di;

import com.san.moviedbclientmvc.common.permissions.Constants;
import com.san.moviedbclientmvc.networking.MoviedbApiService;
import com.san.moviedbclientmvc.common.dialogs.DialogsEventBus;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {

    private Retrofit mRetrofit;
    private DialogsEventBus mDialogsEventBus;

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public MoviedbApiService getMovieApiService() {
        return getRetrofit().create(MoviedbApiService.class);
    }

    public DialogsEventBus getDialogsEventBus() {
        if (mDialogsEventBus == null) {
            mDialogsEventBus = new DialogsEventBus();
        }
        return mDialogsEventBus;
    }
}
