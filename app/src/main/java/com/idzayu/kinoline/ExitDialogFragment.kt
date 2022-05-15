package com.idzayu.kinoline

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.idzayu.kinoline.databinding.FragmentExitDialogBinding
import com.idzayu.kinoline.databinding.FragmentMovieFavoriteBinding


class ExitDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentExitDialogBinding.inflate(inflater)
        init(binding)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    private fun init(binding: FragmentExitDialogBinding){
        binding.buttonExit.setOnClickListener{
            activity?.finish()
        }
        binding.buttonNoExit.setOnClickListener {
            onStop()
        }
    }

}