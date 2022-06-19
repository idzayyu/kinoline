package com.idzayu.kinoline.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.idzayu.kinoline.R
import com.idzayu.kinoline.databinding.FragmentDetailFilmBinding

class DetailFilmFragment : Fragment() {
    private lateinit var dfvm: DetailFilmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailFilmBinding.inflate(inflater)
        init(binding)
        return binding.root
    }

    private fun init(binding: FragmentDetailFilmBinding) {
       dfvm = ViewModelProvider(this)[DetailFilmViewModel::class.java]
       binding.description.text = dfvm.getDescriptionText()
       binding.artMovie.setImageResource(R.drawable.moneyball)
       binding.isLike.setOnClickListener{
           dfvm.onClickLike()
       }

       binding.buttonSaveDetail.setOnClickListener{
           dfvm.addComment(binding.comment.text.toString())
           parentFragmentManager.popBackStack()
       }

    }
}
