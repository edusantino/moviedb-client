package com.san.moviedbclientmvc.ui.moviedetails.usecase

import com.san.moviedbclientmvc.common.BaseObservable
import com.san.moviedbclientmvc.networking.MoviedbApiService
import com.san.moviedbclientmvc.networking.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FetchMovieDetailsUseCase(private val mMoviedbApiService: MoviedbApiService) :
    BaseObservable<FetchMovieDetailsUseCase.Listener?>() {

    interface Listener {
        fun onMovieDetailsFetched(movieDetails: Movie?)
        fun onMovieDetailsFetchFailed()
    }

    fun fetchMovieDetailsAndNotify(movieId: Int) {
        val compositeDisposable = CompositeDisposable()

        compositeDisposable.add(mMoviedbApiService.getMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                notifySuccess(it!!)
            }) {
                run {
                    fetchMovieDetailsAndNotify(movieId)
                    notifyFailure()
                }
            })
    }

    private fun notifyFailure() {
        for (listener in listeners) {
            listener!!.onMovieDetailsFetchFailed()
        }
    }

    private fun notifySuccess(movie: Movie) {
        for (listener in listeners) {
            listener!!.onMovieDetailsFetched(
                Movie(
                    movie.id,
                    movie.original_title,
                    movie.popularity,
                    movie.poster_path,
                    movie.vote_count,
                    movie.release_date,
                    movie.vote_average,
                    movie.overview,
                    movie.releases
                )
            )
        }
    }
}