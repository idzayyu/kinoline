package com.idzayu.kinoline

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieList {
    //private val movieList = arrayListOf(
    //    Movie(R.drawable.grand_budapest_hotel, "THE  GRAND BUDAPEST Hotel"
    //        , "The Grand Budapest Hotel is a 2014 comedy-drama film written and directed by Wes Anderson. Ralph Fiennes leads a seventeen-actor ensemble cast as Monsieur Gustave H., famed concierge of a twentieth-century mountainside resort in the fictional Eastern European country of Zubrowka. When Gustave is framed for the murder of a wealthy dowager (Tilda Swinton), he and his recently befriended protégé Zero (Tony Revolori) embark on a quest for fortune and a priceless Renaissance painting amidst the backdrop of an encroaching fascist regime. Anderson's American Empirical Pictures produced the film in association with Studio Babelsberg, Fox Searchlight Pictures, and Indian Paintbrush's Scott Rudin and Steven Rales. Fox Searchlight supervised the commercial distribution, and The Grand Budapest Hotel's funding was sourced through Indian Paintbrush and German government-funded tax rebates.")
    //,Movie(R.drawable.the_shawshank_redemption, "The Shawshank Redemption"
    //        ,"The Shawshank Redemption is a 1994 American drama film written and directed by Frank Darabont, based on the 1982 Stephen King novella Rita Hayworth and Shawshank Redemption. It tells the story of banker Andy Dufresne (Tim Robbins), who is sentenced to life in Shawshank State Penitentiary for the murders of his wife and her lover, despite his claims of innocence. Over the following two decades, he befriends a fellow prisoner, contraband smuggler Ellis \"Red\" Redding (Morgan Freeman), and becomes instrumental in a money-laundering operation led by the prison warden Samuel Norton (Bob Gunton). William Sadler, Clancy Brown, Gil Bellows, and James Whitmore appear in supporting roles.")
    //,Movie(R.drawable.moneyball, "Moneyball",
    //        "Фильм по книге Майкла M. Льюиса, изданной в 2003 году, об Оклендской бейсбольной команде и ее генеральном менеджере, Билли Бине. Его цель - создать конкурентоспособную бейсбольную команду, несмотря на финансовые трудности."
    //    ))
    var isSuccessful = 2
    private val movieList = ArrayList<Movie>()
    private var movieFavoriteList = ArrayList<Movie>()
    private var positionSelectedMovie = 0

    fun setPositionSelectedMovie(position: Int) {
        positionSelectedMovie = position
        }

    fun getPositionSelectedMovie(): Int{
        return  positionSelectedMovie
    }


    fun getMovieList(): ArrayList<Movie> {

        return movieList
    }
    fun getMovieFavoriteList(): ArrayList<Movie> {

        return movieFavoriteList
    }

    fun initList() {

    }
}