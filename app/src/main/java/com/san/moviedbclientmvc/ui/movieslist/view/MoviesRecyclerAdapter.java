package com.san.moviedbclientmvc.ui.movieslist.view;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.moviedbclientmvc.networking.model.Movie;
import com.san.moviedbclientmvc.common.factory.ViewMvcFactory;
import com.san.moviedbclientmvc.ui.movieslist.movieslistitem.MoviesListItemViewContract;

import java.util.ArrayList;
import java.util.List;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MyViewHolder>
        implements MoviesListItemViewContract.Listener {

    public interface Listener {
        void onMovieItemClicked(Movie movie);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final MoviesListItemViewContract mViewMvc;

        public MyViewHolder(MoviesListItemViewContract viewMvc) {
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }

    }

    private final Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;

    private List<Movie> mMovies = new ArrayList<>();

    public MoviesRecyclerAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        mListener = listener;
        mViewMvcFactory = viewMvcFactory;
    }

    public void bindMovies(List<Movie> movies) {
        mMovies = new ArrayList<>(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MoviesListItemViewContract viewMvc = mViewMvcFactory.getQuestionsListItemViewMvc(parent);
        viewMvc.registerListener(this);
        return new MyViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mViewMvc.bindMovie(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        mListener.onMovieItemClicked(movie);
    }

}
