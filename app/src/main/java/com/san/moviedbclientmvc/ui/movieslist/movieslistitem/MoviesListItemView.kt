package com.san.moviedbclientmvc.ui.movieslist.movieslistitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.san.moviedbclientmvc.R
import com.san.moviedbclientmvc.common.permissions.Constants
import com.san.moviedbclientmvc.common.views.BaseObservableViewMvc
import com.san.moviedbclientmvc.networking.model.Movie

class MoviesListItemView(inflater: LayoutInflater, parent: ViewGroup?) : BaseObservableViewMvc<MoviesListItemViewContract.Listener>(),
                        MoviesListItemViewContract {

    init {
        rootView = inflater.inflate(R.layout.layout_movie_list_item, parent, false)
        rootView.setOnClickListener { view: View? ->
            for (listener in listeners) {
                listener!!.onMovieItemClicked(mMovie)
            }
        }
    }

    private val mMoviesItemTitleView: TextView? = findViewById(R.id.movieItemTitleView)
    private val mYearItemView: TextView? = findViewById(R.id.yearItemView)
    private val mPosterPathItemView: ImageView? = findViewById(R.id.posterItemView)
    private val mOverview: TextView? = findViewById(R.id.movieDetailsSubDescription)
    private val mUserScore: TextView? = findViewById(R.id.percentItemView)
    private val mUserScoreProgressIndicator: ProgressBar? = findViewById(R.id.progressIndicatorView)

    private var mMovie: Movie? = null

    override fun bindMovie(movie: Movie?) {
        mMovie = movie
        val value = (10.0 * movie!!.vote_average.toDouble()).toInt()
        mMoviesItemTitleView?.text = (movie.original_title)
        mYearItemView!!.text = movie.release_date.substring(0, 4)
        mUserScore!!.text = value.toString()
        mUserScoreProgressIndicator!!.progress = value
        mOverview!!.text = movie.overview

        mPosterPathItemView?.context?.let {
            Glide.with(it)
                .load(Constants.URL_IMG + movie.poster_path)
                .centerCrop()
                .into(mPosterPathItemView)
        }
    }
}