package com.idzayu.kinoline.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.idzayu.kinoline.databinding.PartDefaultLoadStateBinding

typealias TryAgainAction = () -> Unit

/**
 * This adapter is used for rendering the load state (ProgressBar, error message and Try Again button)
 * in the list's header and footer.
 */
class DefaultLoadStateAdapter(
    private val tryAgainAction: TryAgainAction
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PartDefaultLoadStateBinding.inflate(inflater, parent, false)
        return Holder(binding, null, tryAgainAction)
    }

    class Holder(
        private val binding: PartDefaultLoadStateBinding,
        private val swipeRefreshLayout: SwipeRefreshLayout?,
        private val tryAgainAction: TryAgainAction
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tryAgainButton.setOnClickListener { tryAgainAction() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            messageTextView.isVisible = loadState is LoadState.Error
            tryAgainButton.isVisible = loadState is LoadState.Error
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                progressBar.isVisible = false
            } else {
                progressBar.isVisible = loadState is LoadState.Loading
            }
        }
    }

}