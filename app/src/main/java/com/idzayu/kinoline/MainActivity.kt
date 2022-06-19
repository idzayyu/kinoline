package com.idzayu.kinoline

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.idzayu.kinoline.databinding.ActivityMainBinding
import com.idzayu.kinoline.ui.exit.ExitDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity()  {
    lateinit var binding: ActivityMainBinding
    var movieList = MovieList.getMovieList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        App.instance.api.getMovie()
            .enqueue(object: Callback<List<MovieModel>> {
                override fun onResponse(
                    call: Call<List<MovieModel>>,
                    response: Response<List<MovieModel>>
                ) {
                    //movieList.clear()
                    MovieList.isSuccessful = response.code()
                    if(response.isSuccessful){
                        movieList.addAll(response.body()?.map { model ->
                            Movie(
                                model.imageUrl,
                                model.nameFilm,
                                model.description
                            )
                        } ?: emptyList())

                    }

                }

                override fun onFailure(call: Call<List<MovieModel>>, t: Throwable) {
                    Log.d("ssasdas", t.toString())
                }
            })

        MovieList.initList()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.filmListFragment, R.id.movieFavoriteFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


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
                Toast.makeText(this, MovieList.isSuccessful.toString(), Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            val dialog = ExitDialogFragment()
            dialog.show(supportFragmentManager, "dialog")
        }

    }



}