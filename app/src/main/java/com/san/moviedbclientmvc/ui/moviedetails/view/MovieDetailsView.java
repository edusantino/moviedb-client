package com.san.moviedbclientmvc.ui.moviedetails.view;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.san.moviedbclientmvc.common.factory.ViewMvcFactory;
import com.san.moviedbclientmvc.common.permissions.Constants;
import com.san.moviedbclientmvc.networking.model.Countries;
import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.R;
import com.san.moviedbclientmvc.common.toolbar.ToolbarViewMvc;
import com.san.moviedbclientmvc.common.views.BaseObservableViewMvc;

import java.util.List;
import java.util.Objects;


public class MovieDetailsView extends BaseObservableViewMvc<MovieDetailsViewContract.Listener>
        implements MovieDetailsViewContract {


    private final ToolbarViewMvc mToolbarViewMvc;
    private final Toolbar mToolbar;

    private final TextView mMovieDetailsTitle;
    private final TextView mMovieDetailsType;
    private final TextView mMovieDetailSubDescription;
    private final ProgressBar mProgressBar;
    private final ImageView mMovieDetailsPosterView;
    private final TextView mMovieDetailsCertificate;
    private final ConstraintLayout mMovieDetailsClassificationView;
    private final ProgressBar mMovieDetailsUserScoreProgress;
    private final TextView mMovieDetailsUserScore;
    private String mClassificationValue;

    public MovieDetailsView(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {

        setRootView(inflater.inflate(R.layout.layout_movie_details, parent, false));

        mMovieDetailsTitle = findViewById(R.id.movieDetailsTitleView);
        mMovieDetailsType = findViewById(R.id.movieDetailsTypeView);
        mMovieDetailsPosterView = findViewById(R.id.backDropPathView);
        mProgressBar = findViewById(R.id.progress);
        mToolbar = findViewById(R.id.toolbar);
        mMovieDetailSubDescription = findViewById(R.id.movieDetailsSubDescription);
        mMovieDetailsCertificate = findViewById(R.id.certificateView);
        mMovieDetailsClassificationView = findViewById(R.id.like_component);
        mMovieDetailsUserScoreProgress = findViewById(R.id.progressIndicatorView);
        mMovieDetailsUserScore = findViewById(R.id.percentItemView);

        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.addView(mToolbarViewMvc.getRootView());

        mToolbarViewMvc.setTitle(getString(R.string.question_details_screen_title));

        mToolbarViewMvc.enableUpButtonAndListen(() -> {
            for (Listener listener : getListeners()) {
                listener.onNavigateUpClicked();
            }
        });
    }

    @Override
    public void bindMovie(Movie movie) {
        Integer value = (int)(10.0 * movie.getVote_average().doubleValue());

        getReleaseString(Objects.requireNonNull(movie.getReleases().getCountries()));
        if (mClassificationValue.equals("")) {
            mMovieDetailsCertificate.setText("?");
        } else {
            mMovieDetailsCertificate.setText(mClassificationValue);
        }

        mMovieDetailsClassificationView.setBackground(ContextCompat.getDrawable(getRootView().getContext(), setupClassificationView()));
        mMovieDetailSubDescription.setText(movie.getOverview());
        mMovieDetailsUserScore.setText(value.toString());
        mMovieDetailsUserScoreProgress.setProgress(value);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mMovieDetailsTitle.setText(Html.fromHtml(movie.getOriginal_title(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            mMovieDetailsTitle.setText(Html.fromHtml(movie.getOriginal_title()));
        }

        Glide.with(mMovieDetailsPosterView.getContext())
                .load(Constants.URL_IMG + movie.getPoster_path())
                .centerCrop()
                .into(mMovieDetailsPosterView);


    }

    private void getReleaseString(List<Countries> countries) {
        for (Countries country: countries) {
            if (country.getIso_3166_1().equals("BR")) {
                mClassificationValue = country.getCertification();
                break;
            }
        }
    }

    private int setupClassificationView() {
        int val = 0;

        switch (mClassificationValue==null? "":mClassificationValue) {
            case "L":
                val = R.drawable.background_l;
                break;
            case "10":
                val = R.drawable.background_10;
                break;

            case "12":
                val = R.drawable.background_12;
                break;

            case "14":
                val = R.drawable.background_14;
                break;

            case "16":
                val =  R.drawable.background_16;
                break;

            case "18":
                val =  R.drawable.background_18;
                break;

            case "":
                val = R.drawable.background_interr;
                break;
        }
        return val;
    }

    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void initViews() {

    }
}
