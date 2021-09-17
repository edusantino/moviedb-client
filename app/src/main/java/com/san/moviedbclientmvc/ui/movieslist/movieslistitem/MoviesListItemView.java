package com.san.moviedbclientmvc.ui.movieslist.movieslistitem;

import com.bumptech.glide.Glide;
import com.san.moviedbclientmvc.common.permissions.Constants;
import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.R;
import com.san.moviedbclientmvc.common.views.BaseObservableViewMvc;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MoviesListItemView extends BaseObservableViewMvc<MoviesListItemViewContract.Listener>
        implements MoviesListItemViewContract {

    private final TextView mMovieItemTitleView;
    private final TextView mYearItemView;
    private final TextView mTypeItemView;
    private final ImageView mPosterPathItemView;

    private Movie mMovie;

    public MoviesListItemView(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_movie_list_item, parent, false));

        mMovieItemTitleView = findViewById(R.id.movieItemTitleView);
        mYearItemView = findViewById(R.id.yearItemView);
        mTypeItemView = findViewById(R.id.typeItemView);
        mPosterPathItemView = findViewById(R.id.posterItemView);
        getRootView().setOnClickListener( view -> {
            for (Listener listener : getListeners()) {
                listener.onMovieItemClicked(mMovie);
            }
        });
    }

    @Override
    public void bindMovie(Movie movie) {
        mMovie = movie;
        mMovieItemTitleView.setText(movie.getOriginal_title());
        mYearItemView.setText(movie.getRelease_date().substring(0, 4));
        //mTypeItemView.setText();
        Glide.with(mPosterPathItemView.getContext())
                .load(Constants.URL_IMG + movie.getPoster_path())
                .centerCrop()
                .into(mPosterPathItemView);
    }
}
