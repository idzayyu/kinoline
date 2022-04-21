package com.idzayu.kinoline

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.idzayu.kinoline.databinding.MovieItemBinding

class MovieAdapter( private val movieList: ArrayList<Movie>, private val  onMovieDetailClickListener: OnMovieDetailClickListener) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    class MovieHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MovieItemBinding.bind(item)


        @SuppressLint("ResourceAsColor")
        fun bind(
            position: Int,
            movie: Movie,
            onMovieDetailClickListener: OnMovieDetailClickListener
        ) = with(binding){
            artMovie.setImageResource(movie.imageId)
            textView.text = movie.nameFilm
            if (movie.isSelected) textView.setBackgroundResource(R.color.purple_500)
            if (movie.isLike) {
                imageLiked.visibility = View.VISIBLE
                //like.text = movie.comment
            }

            if (movie.comment != ""){
                textView2.visibility = View.VISIBLE
                textView2.text = movie.comment
            }
            buttonDetail.setOnClickListener {

                textView.setBackgroundResource(R.color.purple_500)
                movie.isSelected = true
                onMovieDetailClickListener.onMovieDetailClicked(position)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(position,movieList[position],onMovieDetailClickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }



}