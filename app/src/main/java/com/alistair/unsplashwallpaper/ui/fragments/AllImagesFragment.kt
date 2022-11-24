package com.alistair.unsplashwallpaper.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.alistair.unsplashwallpaper.R
import com.alistair.unsplashwallpaper.databinding.FragmentAllImagesBinding
import com.alistair.unsplashwallpaper.ui.ImageViewModel
import com.alistair.unsplashwallpaper.ui.ImagesActivity

class AllImagesFragment : Fragment() {

    private var _binding: FragmentAllImagesBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding  = FragmentAllImagesBinding.inflate(inflater, container, false)
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