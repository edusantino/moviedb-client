package com.san.moviedbclientmvc.ui.movieslist.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.san.moviedbclientmvc.R;
import com.san.moviedbclientmvc.common.factory.ViewMvcFactory;
import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.common.navdrawer.NavDrawerHelper;
import com.san.moviedbclientmvc.common.toolbar.ToolbarViewMvc;
import com.san.moviedbclientmvc.common.views.BaseObservableViewMvc;
import com.san.moviedbclientmvc.ui.movieslist.view.controller.MoviesListViewContract;

import java.util.List;

public class MoviesListView extends BaseObservableViewMvc<MoviesListViewContract.Listener>
        implements MoviesListViewContract, MoviesRecyclerAdapter.Listener {

    private final ToolbarViewMvc mToolbarViewMvc;
    private final NavDrawerHelper mNavDrawerHelper;

    private final Toolbar mToolbar;
    private final RecyclerView mRecyclerQuestions;
    private final MoviesRecyclerAdapter mAdapter;
    private final ProgressBar mProgressBar;

    public MoviesListView(LayoutInflater inflater,
                          @Nullable ViewGroup parent,
                          NavDrawerHelper navDrawerHelper,
                          ViewMvcFactory viewMvcFactory) {
        mNavDrawerHelper = navDrawerHelper;
        setRootView(inflater.inflate(R.layout.layout_movie_list, parent, false));

        mRecyclerQuestions = findViewById(R.id.moviesListRecyclerView);
        mRecyclerQuestions.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MoviesRecyclerAdapter(this, viewMvcFactory);
        mRecyclerQuestions.setAdapter(mAdapter);

        mProgressBar = findViewById(R.id.progress);

        mToolbar = findViewById(R.id.toolbar);
        mToolbarViewMvc = viewMvcFactory.getToolbarViewMvc(mToolbar);
        initToolbar();
    }

    private void initToolbar() {
        mToolbarViewMvc.setTitle(getString(R.string.questions_list_screen_title));
        mToolbar.addView(mToolbarViewMvc.getRootView());
        mToolbarViewMvc.enableHamburgerButtonAndListen(new ToolbarViewMvc.HamburgerClickListener() {
            @Override
            public void onHamburgerClicked() {
                mNavDrawerHelper.openDrawer();
            }
        });
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        for (Listener listener : getListeners()) {
            listener.onMovieItemClicked(movie);
        }
    }

    @Override
    public void bindMovies(List<Movie> movies) {
        mAdapter.bindMovies(movies);
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
