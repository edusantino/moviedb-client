package com.san.moviedbclientmvc.ui.moviedetails.view;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.san.moviedbclientmvc.common.permissions.Constants;
import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.R;
import com.san.moviedbclientmvc.common.factory.ViewMvcFactory;
import com.san.moviedbclientmvc.common.toolbar.ToolbarViewMvc;
import com.san.moviedbclientmvc.common.views.BaseObservableViewMvc;


public class MovieDetailsView extends BaseObservableViewMvc<MovieDetailsViewContract.Listener>
        implements MovieDetailsViewContract {


    private final ToolbarViewMvc mToolbarViewMvc;
    private final Toolbar mToolbar;

    private final TextView mMovieDetailsTitle;
    private final TextView mMovieDetailsType;
    private final ProgressBar mProgressBar;
    private final ImageView mMovieDetailsPosterView;

    public MovieDetailsView(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {

        setRootView(inflater.inflate(R.layout.layout_movie_details, parent, false));

        mMovieDetailsTitle = findViewById(R.id.movieDetailsTitleView);
        mMovieDetailsType = findViewById(R.id.movieDetailsTypeView);
        mMovieDetailsPosterView = findViewById(R.id.movieDetailsPosterView);
        mProgressBar = findViewById(R.id.progress);
        mToolbar = findViewById(R.id.toolbar);

        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);

        initToolbar();
    }

    private void initToolbar() {
        mToolbar.addView(mToolbarViewMvc.getRootView());

        mToolbarViewMvc.setTitle(getString(R.string.question_details_screen_title));

        mToolbarViewMvc.enableUpButtonAndListen(new ToolbarViewMvc.NavigateUpClickListener() {
            @Override
            public void onNavigateUpClicked() {
                for (Listener listener : getListeners()) {
                    listener.onNavigateUpClicked();
                }
            }
        });
    }

    @Override
    public void bindMovie(Movie movie) {
        String questionTitle = movie.getOriginal_title();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mMovieDetailsTitle.setText(Html.fromHtml(questionTitle, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mMovieDetailsTitle.setText(Html.fromHtml(questionTitle));
        }

        Glide.with(mMovieDetailsPosterView.getContext())
                .load(Constants.URL_IMG + movie.getPoster_path())
                .centerCrop()
                .into(mMovieDetailsPosterView);
    }


    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.GONE);
    }
}
