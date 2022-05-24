package com.idzayu.kinoline

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idzayu.kinoline.databinding.MovieItemBinding

class MovieAdapter(private val movieList: ArrayList<Movie>,
                   private val listener: NewsClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    inner class MovieHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MovieItemBinding.bind(item)


        @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
        fun bind(
            position: Int,
            movie: Movie,
            listener: NewsClickListener
        ) = with(binding) {
            artMovie.setImageResource(movie.imageId)
            textView.text = movie.nameFilm
            if (movie.isSelected) textView.setBackgroundResource(R.color.purple_500)
            if (movie.isLike) {
                imageLiked.visibility = View.VISIBLE
            }

            if (movie.comment != "") {
                textView2.visibility = View.VISIBLE
                textView2.text = movie.comment
            }
            buttonDetail.setOnClickListener {
                textView.setBackgroundResource(R.color.purple_500)
                movie.isSelected = true
                listener.onMovieDetailClicked(position)
            }

            artMovie.setOnLongClickListener{
                listener.onFavoriteClick(movie, position)
                notifyDataSetChanged()
                return@setOnLongClickListener true
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        if (movieList.size > 0){
            holder.bind(position,movieList[position],listener)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    interface NewsClickListener{
        fun onNewsClick(movie: Movie, position: Int)
        fun onFavoriteClick(movie: Movie, position: Int)
        fun onMovieDetailClicked(position: Int)
    }

}