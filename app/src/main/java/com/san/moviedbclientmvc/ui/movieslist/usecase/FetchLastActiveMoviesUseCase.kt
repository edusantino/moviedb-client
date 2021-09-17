package com.san.moviedbclientmvc.ui.movieslist.usecase

import com.san.moviedbclientmvc.common.BaseObservable
import com.san.moviedbclientmvc.networking.MoviedbApiService
import com.san.moviedbclientmvc.networking.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FetchLastActiveMoviesUseCase(private val mMoviedbApiService: MoviedbApiService) :
    BaseObservable<FetchLastActiveMoviesUseCase.Listener?>() {

    interface Listener {
        fun onLastActiveQuestionsFetched(moviesList: List<Movie>?)
        fun onLastActiveQuestionsFetchFailed()
    }

    fun fetchLastActiveMoviesAndNotify() {
        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(mMoviedbApiService.getMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                notifySuccess(it?.results!!)
            }) {
                run {
                    fetchLastActiveMoviesAndNotify()
                    notifyFailure()
                }
            })
    }

    private fun notifyFailure() {
        for (listener in listeners) {
            listener!!.onLastActiveQuestionsFetchFailed()
        }
    }

    private fun notifySuccess(movieSchemas: List<Movie>?) {
        for (listener in listeners) {
            listener!!.onLastActiveQuestionsFetched(movieSchemas)
        }
    }
}