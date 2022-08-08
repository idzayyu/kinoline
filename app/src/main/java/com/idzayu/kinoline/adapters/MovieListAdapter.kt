package com.idzayu.kinoline.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idzayu.kinoline.R
import com.idzayu.kinoline.databinding.MovieItemBinding
import com.idzayu.kinoline.model.movies.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class MovieListAdapter(private val listener: NewsClickListener
): PagingDataAdapter<Movie, MovieListAdapter.MovieHolder>(MovieDiffCallback()) {

    class MovieHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root){
        private lateinit var likeAnim: Animation

        fun bind(movie: Movie,
                 position: Int,
                 listener: NewsClickListener
        ) = with(binding) {

            loadUserPhoto(artMovie, movie.imageUrl)
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
                return@setOnLongClickListener true
            }

        }

        private fun loadUserPhoto(imageView: ImageView, url: String) {
            val context = imageView.context
            if (url.isNotBlank()) {
                Glide.with(context)
                    .load(url)
                    .placeholder(android.R.drawable.ic_popup_sync)
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageView)
            } else {
                Glide.with(context)
                    .load(android.R.drawable.ic_popup_sync)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieItemBinding
                .bind(LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.movie_item, parent, false))
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie,position, listener)
    }

    interface NewsClickListener{
        fun onFavoriteClick(movie: Movie, position: Int)
        fun onMovieDetailClicked(position: Int)
    }

}

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}