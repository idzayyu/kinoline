package com.idzayu.kinoline.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idzayu.kinoline.model.movies.Movie
import com.idzayu.kinoline.R
import com.idzayu.kinoline.databinding.MovieItemBinding

class MovieAdapter(private val movieList: ArrayList<Movie>,
                   private val listener: NewsClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private lateinit var likeAnim: Animation


    inner class MovieHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MovieItemBinding.bind(item)


        @SuppressLint("ResourceAsColor", "NotifyDataSetChanged", "ResourceType")
        fun bind(
            position: Int,
            movie: Movie,
            listener: NewsClickListener
        ) = with(binding) {
            Glide.with(artMovie)
                .load(movie.imageUrl)
                .placeholder(android.R.drawable.ic_popup_sync)
                .error(android.R.drawable.stat_notify_error)
                .into(artMovie)

            textView.text = movie.nameFilm
            likeAnim = AnimationUtils.loadAnimation(imageView2.context, R.anim.show_like)
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
                val river = movie.isfavorite
                listener.onFavoriteClick(movie, position)
                if (!river){
                    imageView2.startAnimation(likeAnim)
                    imageLiked.visibility = View.VISIBLE
                }
                else {
                    notifyDataSetChanged()
                }
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