package com.alistair.unsplashwallpaper.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alistair.unsplashwallpaper.R
import com.alistair.unsplashwallpaper.databinding.FragmentDetailImageBinding
import com.alistair.unsplashwallpaper.ui.ImageViewModel
import com.alistair.unsplashwallpaper.ui.ImagesActivity


class DetailImageFragment : Fragment() {
    private var _binding: FragmentDetailImageBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ImagesActivity).viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}