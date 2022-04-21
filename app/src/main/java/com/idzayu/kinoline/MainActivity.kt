package com.idzayu.kinoline

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.idzayu.kinoline.databinding.ActivityMainBinding
import kotlin.math.log


class MainActivity : AppCompatActivity() ,OnMovieDetailClickListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var start:
            ActivityResultLauncher<Intent>
    private val movieList = MovieList.getMovieList()

    val movieAdapter = MovieAdapter(movieList, this)
    private var dataComment = ""
    var dataIsLike = false


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this,1)
        binding.recyclerView.adapter = movieAdapter
        movieAdapter.notifyDataSetChanged()

        start = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            val data = result.data

            if (result.resultCode == RESULT_OK && data != null){
                dataComment = data.getStringExtra("comment").toString()
                dataIsLike = data.getBooleanExtra("isLike", false)
                val position = data.getIntExtra("position",0)

                movieList[position].comment = dataComment

                movieAdapter.notifyDataSetChanged()
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addBut -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Пошли в кино")
                sendIntent.type = "text/plain"
                try {
                    startActivity(sendIntent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(applicationContext, "Install Telegram", Toast.LENGTH_LONG).show()
                }
            }
        }
        return true
    }

    override fun onMovieDetailClicked(position: Int) {
        val intent = Intent(this, MovieDetail::class.java)
        intent.putExtra("image",movieList[position].imageId)
        intent.putExtra("position",position)
        intent.putExtra("desc",movieList[position].description)

        start.launch(intent)
    }

}