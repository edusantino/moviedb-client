package com.san.moviedbclientmvc.ui.movieslist.movieslistitem;

import com.bumptech.glide.Glide;
import com.san.moviedbclientmvc.common.permissions.Constants;
import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.R;
import com.san.moviedbclientmvc.common.views.BaseObservableViewMvc;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MoviesListItemView extends BaseObservableViewMvc<MoviesListItemViewContract.Listener>
        implements MoviesListItemViewContract {

    private final TextView mMovieItemTitleView;
    private final TextView mYearItemView;
    private final TextView mTypeItemView;
    private final ImageView mPosterPathItemView;
    private final TextView mOverview;
    private final TextView mUserScore;
    private final ProgressBar mUserScoreProgressIndicator;

    private final TextView mMovieDetailsCertificate;
    private final ConstraintLayout mMovieDetailsClassificationView;
    private String mClassificationValue;

    private Movie mMovie;

    public MoviesListItemView(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_movie_list_item, parent, false));

        mMovieItemTitleView = findViewById(R.id.movieItemTitleView);
        mYearItemView = findViewById(R.id.yearItemView);
        mTypeItemView = findViewById(R.id.typeItemView);
        mPosterPathItemView = findViewById(R.id.posterItemView);
        mOverview = findViewById(R.id.movieDetailsSubDescription);
        mUserScore = findViewById(R.id.percentItemView);
        mUserScoreProgressIndicator = findViewById(R.id.progressIndicatorView);

        mMovieDetailsCertificate = findViewById(R.id.certificateView);
        mMovieDetailsClassificationView = findViewById(R.id.like_component);

        getRootView().setOnClickListener( view -> {
            for (Listener listener : getListeners()) {
                listener.onMovieItemClicked(mMovie);
            }
        });
    }

    @Override
    public void bindMovie(Movie movie) {
        mMovie = movie;
        Integer value = (int)(10.0 * movie.getVote_average().doubleValue());
        mMovieItemTitleView.setText(movie.getOriginal_title());
        mYearItemView.setText(movie.getRelease_date().substring(0, 4));
        mUserScore.setText(value.toString());
        mUserScoreProgressIndicator.setProgress(value);
        mOverview.setText(movie.getOverview());

        Glide.with(mPosterPathItemView.getContext())
                .load(Constants.URL_IMG + movie.getPoster_path())
                .centerCrop()
                .into(mPosterPathItemView);

        // Classification area

    }
}
