package com.san.moviedbclientmvc.ui.movieslist.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.san.moviedbclientmvc.common.factory.ViewMvcFactory
import com.san.moviedbclientmvc.networking.model.Movie
import com.san.moviedbclientmvc.ui.movieslist.movieslistitem.MoviesListItemViewContract
import java.util.*

class MoviesRecyclerAdapter(private val mListener: Listener,
                             private val mViewMvcFactory: ViewMvcFactory
                             ) : RecyclerView.Adapter<MoviesRecyclerAdapter.MyViewHolder>(),
    MoviesListItemViewContract.Listener {

    interface Listener {
        fun onMovieItemClicked(movie: Movie?)
    }

    class MyViewHolder(val mViewMvc: MoviesListItemViewContract) : RecyclerView.ViewHolder(
        mViewMvc.rootView!!
    )

    private var mMovies: List<Movie> = ArrayList()
    fun bindMovies(movies: List<Movie>?) {
        mMovies = ArrayList(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewMvc = mViewMvcFactory.getQuestionsListItemViewMvc(parent)
        viewMvc.registerListener(this)
        return MyViewHolder(viewMvc)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mViewMvc.bindMovie(mMovies[position])
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    override fun onMovieItemClicked(movie: Movie?) {
        mListener.onMovieItemClicked(movie)
    }
}