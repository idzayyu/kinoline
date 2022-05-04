package com.idzayu.kinoline.ui.list

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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.idzayu.kinoline.*
import com.idzayu.kinoline.databinding.FragmentFilmListBinding

class FilmListFragment : Fragment() {

    private val movieList = MovieList.getMovieList()
    private val movieFavoriteList = MovieList.getMovieFavoriteList()
    private lateinit var start:
            ActivityResultLauncher<Intent>

    private var dataComment = ""
    var dataIsLike = false

    val movieAdapter = MovieAdapter(movieList, object: MovieAdapter.NewsClickListener {
        override fun onNewsClick(movie: Movie, position: Int) {
            Toast.makeText(activity, "Long click 1", Toast.LENGTH_SHORT).show()
        }

        override fun onFavoriteClick(movie: Movie, position: Int) {
            Toast.makeText(activity, "Add to favorite", Toast.LENGTH_SHORT).show()
            movieFavoriteList.add(movie)
        }

        override fun onMovieDetailClicked(position: Int) {

            val intent = Intent(activity, MovieDetail::class.java)
            intent.putExtra("image",movieList[position].imageId)
            intent.putExtra("position",position)
            intent.putExtra("IsFavorite",false)
            intent.putExtra("desc",movieList[position].description)

            start.launch(intent)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilmListBinding.inflate(inflater)
        init(binding)
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