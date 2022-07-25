package com.idzayu.kinoline.ui.main

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
import com.idzayu.kinoline.R
import com.idzayu.kinoline.databinding.ActivityMainBinding
import com.idzayu.kinoline.model.movies.Movie
import com.idzayu.kinoline.model.movies.Repository.ApiMovieRepository
import com.idzayu.kinoline.model.movies.Repository.MovieList
import com.idzayu.kinoline.model.movies.Repository.room.AppDataBase
import com.idzayu.kinoline.ui.exit.ExitDialogFragment
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity()  {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



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

    override fun onDestroy() {
        Executors.newSingleThreadExecutor().execute {
            Runnable {
                val appDb = AppDataBase.getInstance(this)?.getMovieDao()
                appDb?.insert(MovieList.getMovieEntity())
                appDb?.insertFavorite(MovieList.getMovieFavoriteEntity())
            }.run()
        }
        super.onDestroy()
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