package com.idzayu.kinoline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.idzayu.kinoline.databinding.ActivityMovieDetailBinding

class MovieDetail() : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    var flag = false
    val movieList = MovieList.getMovieList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0)

        binding.description.text = intent.getStringExtra("desc")
        binding.imageView.setImageResource(intent.getIntExtra("image", 0))

        binding.isLike.setOnClickListener{
            flag = !flag
            movieList[position].isLike = flag
            Log.d("ASS", movieList[position].isLike.toString())
        }

        binding.buttonSaveDetail.setOnClickListener{
            val data = Intent()
            data.putExtra("comment", binding.comment.text.toString())
            movieList[position].comment = binding.comment.text.toString()
            data.putExtra("isliked", flag)
            data.putExtra("position", position)
            setResult(RESULT_OK, data)
            finish()
        }

    }
}