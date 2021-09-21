package com.san.moviedbclientmvc.networking

import com.san.moviedbclientmvc.networking.model.Movie
import com.san.moviedbclientmvc.networking.model.SimilarMovies
import com.san.moviedbclientmvc.common.permissions.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviedbApiService {
    @GET("3/movie/{id}" + "?api_key=" + Constants.API_KEY + Constants.LANGUAGE)
    fun getMovie(@Path("id") movieId: Int): Single<Movie?>

    @GET("3/movie/" + Constants.FIXED_MOVIE + "/similar" + "?api_key=" + Constants.API_KEY)
    fun getMovieList(): Single<SimilarMovies?>

    @GET("3/movie/{id}/release_dates" + "?api_key=" + Constants.API_KEY)
    fun getMovieReleaseDate(@Path("id") movieId: Int): Single<MovieReleaseDates?>
}