package com.idzayu.kinoline

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.idzayu.kinoline.databinding.FragmentDetailFilmBinding


class DetailFilmFragment : Fragment() {
    var flag = false
    val movieList = MovieList.getMovieList()
    val movieFavoriteList = MovieList.getMovieFavoriteList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailFilmBinding.inflate(inflater)
        init(binding)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init(binding: FragmentDetailFilmBinding) {
                val position = MovieList.getPositionSelectedMovie()

                val isFavorite = movieList[position].isfavorite

                binding.description.text = movieList[position].description
                binding.artMovie.setImageResource(movieList[position].imageId)

                binding.isLike.setOnClickListener{
                    flag = !flag
                    if (isFavorite) {
                        movieFavoriteList[position].isLike = flag
                    }
                    else{
                        movieList[position].isLike = flag
                    }
                }

                binding.buttonSaveDetail.setOnClickListener{
                    movieList[position].comment = binding.comment.text.toString()
                    parentFragmentManager.popBackStack()
                }

            }



}
