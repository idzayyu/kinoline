package com.idzayu.kinoline.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.idzayu.kinoline.*
import kotlinx.coroutines.flow.*
import com.idzayu.kinoline.adapters.DefaultLoadStateAdapter
import com.idzayu.kinoline.model.movies.Repository.MovieList
import com.idzayu.kinoline.adapters.MovieListAdapter
import com.idzayu.kinoline.adapters.TryAgainAction
import com.idzayu.kinoline.databinding.FragmentFilmListBinding
import com.idzayu.kinoline.model.movies.Movie
import com.idzayu.kinoline.ui.detail.DetailFilmFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ua.cn.stu.paging.simpleScan

@ExperimentalCoroutinesApi
@FlowPreview
class FilmListFragment : Fragment() {

    private lateinit var filmListViewModel: FilmListViewModel
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        filmListViewModel = ViewModelProvider(this)[FilmListViewModel::class.java]
        val binding = FragmentFilmListBinding.inflate(inflater)
        init(binding)
        return binding.root
    }

    private fun init(binding: FragmentFilmListBinding) {
        if(MovieList.movieList.size == 0){
            context?.let {
                MovieList.addMovieDb(it.applicationContext)
                MovieList.setPageIndex(it.applicationContext)
            }
        }

        setupMovieList(binding)

        setupSwipeToRefresh(binding)
    }
    private fun setupMovieList(fragmentFilmListBinding: FragmentFilmListBinding) {
        val movieAdapter = MovieListAdapter(object: MovieListAdapter.NewsClickListener {
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
        fragmentFilmListBinding.apply {
            recyclerView.adapter = movieAdapter

            // in case of loading errors this callback is called when you tap the 'Try Again' button
            val tryAgainAction: TryAgainAction = { movieAdapter.retry() }

            val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)

            // combined adapter which shows both the list of users + footer indicator when loading pages
            val adapterWithLoadState = movieAdapter.withLoadStateFooter(footerAdapter)

            recyclerView.layoutManager = LinearLayoutManager(swipeRefreshLayout.context)
            recyclerView.adapter = adapterWithLoadState
            (recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false


            mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
                fragmentFilmListBinding.loadStateView,
                fragmentFilmListBinding.swipeRefreshLayout,
                tryAgainAction
            )
            //setupSwipeToRefresh(binding)

        }


        observeMovie(movieAdapter)
        observeLoadState(movieAdapter)

        //handleScrollingToTopWhenSearching(movieAdapter)
        handleListVisibility(movieAdapter, binding = fragmentFilmListBinding)

    }

    private fun observeMovie(adapter: MovieListAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            filmListViewModel.moviesFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: MovieListAdapter) {
        // you can also use adapter.addLoadStateListener
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                // main indicator in the center of the screen
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun handleListVisibility(adapter: MovieListAdapter, binding: FragmentFilmListBinding) = lifecycleScope.launch {
        // list should be hidden if an error is displayed OR if items are being loaded after the error:
        // (current state = Error) OR (prev state = Error)
        //   OR
        // (before prev state = Error, prev state = NotLoading, current state = Loading)
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 5)
            .collectLatest { (_, _, beforePrevious, previous, current) ->
                binding.recyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(adapter: MovieListAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }

    private fun setupSwipeToRefresh(binding: FragmentFilmListBinding) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            filmListViewModel.refresh()
        }
    }
}
