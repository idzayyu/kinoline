package com.idzayu.kinoline.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.idzayu.kinoline.Movie
import com.idzayu.kinoline.MovieDetail
import com.idzayu.kinoline.MovieList
import com.idzayu.kinoline.databinding.FragmentMovieFavoriteBinding
import com.idzayu.kinoline.MovieAdapter

class MovieFavoriteFragment : Fragment() {
    private lateinit var start:
            ActivityResultLauncher<Intent>
    val movieList = MovieList.getMovieFavoriteList()

    private var dataComment = ""
    var dataIsLike = false
    private val movieAdapter = MovieAdapter(movieList, object: MovieAdapter.NewsClickListener {
        override fun onNewsClick(movie: Movie, position: Int) {
            Toast.makeText(activity, "Long click 1", Toast.LENGTH_SHORT).show()
        }

        override fun onFavoriteClick(movie: Movie, position: Int) {
            Toast.makeText(activity, "Remove to favorite", Toast.LENGTH_SHORT).show()
            movieList.remove(movie)
        }

        override fun onMovieDetailClicked(position: Int) {

            val intent = Intent(activity, MovieDetail::class.java)
            intent.putExtra("image",movieList[position].imageId)
            intent.putExtra("position",position)
            intent.putExtra("IsFavorite",true)
            intent.putExtra("desc",movieList[position].description)

            start.launch(intent)
        }
    })

    private lateinit var viewModel: MovieFavoriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieFavoriteBinding.inflate(inflater)
        init(binding)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init(binding: FragmentMovieFavoriteBinding) {

        binding.apply {
            favoriteRV.layoutManager = GridLayoutManager(activity, 1)
            favoriteRV.adapter = movieAdapter
        }
        start = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            val data = result.data

            if (result.resultCode == AppCompatActivity.RESULT_OK && data != null){
                dataComment = data.getStringExtra("comment").toString()
                dataIsLike = data.getBooleanExtra("isLike", false)
                val position = data.getIntExtra("position",0)

                movieList[position].comment = dataComment

                movieAdapter.notifyDataSetChanged()
            }

        }

    }

}
