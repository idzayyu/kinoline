package com.idzayu.kinoline.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.idzayu.kinoline.*
import com.idzayu.kinoline.databinding.FragmentFilmListBinding
import com.idzayu.kinoline.ui.detail.DetailFilmFragment

class FilmListFragment : Fragment() {

    private val movieList = MovieList.getMovieList()
    private lateinit var filmListViewModel: FilmListViewModel

    private val movieAdapter = MovieAdapter(movieList, object: MovieAdapter.NewsClickListener {
        override fun onNewsClick(movie: Movie, position: Int) {
            Toast.makeText(activity, "Long click 1", Toast.LENGTH_SHORT).show()
        }

        override fun onFavoriteClick(movie: Movie, position: Int) {
            if (filmListViewModel.onFavoriteClick(movie, position)) Toast.makeText(activity, "Add to favorite", Toast.LENGTH_SHORT).show()
            else Toast.makeText(activity, "Film has already been added to favorites", Toast.LENGTH_SHORT).show()

        }

        override fun onMovieDetailClicked(position: Int) {
            MovieList.setPositionSelectedMovie(position)
            val detailFragment = DetailFilmFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment_activity_main,detailFragment)
                .addToBackStack("Main")
                .commit()

        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilmListBinding.inflate(inflater)
        init(binding)
        filmListViewModel = ViewModelProvider(this)[FilmListViewModel::class.java]
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun init(binding: FragmentFilmListBinding) {

        binding.apply {
            recyclerView.adapter = movieAdapter
            val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            activity?.let { it ->
                ContextCompat.getDrawable(it, R.drawable.divider_drawable)
                    ?.let { dividerItemDecoration.setDrawable(it) }
            }
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

}