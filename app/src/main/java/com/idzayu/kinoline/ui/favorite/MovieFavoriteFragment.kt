package com.idzayu.kinoline.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.idzayu.kinoline.*
import com.idzayu.kinoline.databinding.FragmentMovieFavoriteBinding
import com.idzayu.kinoline.ui.detail.DetailFilmFragment

class MovieFavoriteFragment : Fragment() {
    val movieList = MovieList.getMovieFavoriteList()
    private lateinit var movieFavoriteViewModel: MovieFavoriteViewModel

    private val movieAdapter = MovieAdapter(movieList, object: MovieAdapter.NewsClickListener {
        override fun onNewsClick(movie: Movie, position: Int) {
            Toast.makeText(activity, "Long click 1", Toast.LENGTH_SHORT).show()
        }

        override fun onFavoriteClick(movie: Movie, position: Int) {
            movieFavoriteViewModel.onFavoriteClick(movie, position)
            Toast.makeText(activity, "Remove to favorite", Toast.LENGTH_SHORT).show()
        }

        override fun onMovieDetailClicked(position: Int) {
            val detailFragment = DetailFilmFragment()
            MovieList.setPositionSelectedMovie(position)
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main,detailFragment)
                .addToBackStack("Favorite")
                .commit()

        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieFavoriteBinding.inflate(inflater)
        init(binding)
        movieFavoriteViewModel = ViewModelProvider(this)[MovieFavoriteViewModel::class.java]
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init(binding: FragmentMovieFavoriteBinding) {

        binding.apply {
            favoriteRV.layoutManager = GridLayoutManager(activity, 1)
            favoriteRV.adapter = movieAdapter
        }
    }
}
