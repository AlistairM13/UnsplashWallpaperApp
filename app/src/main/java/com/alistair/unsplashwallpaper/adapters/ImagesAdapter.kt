package com.alistair.unsplashwallpaper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alistair.unsplashwallpaper.databinding.ImageSingleItemBinding
import com.alistair.unsplashwallpaper.models.Image
import com.bumptech.glide.Glide

class ImagesAdapter: RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {
private lateinit var binding: ImageSingleItemBinding
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Image>(){
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.urls.regular == newItem.urls.regular
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImagesAdapter.ImageViewHolder, position: Int) {

        val image = differ.currentList[position]
        holder.itemView.apply{
            Glide.with(this).load(image.urls.regular).into(binding.ivImage)
            setOnItemClickListener {
                onItemClickListener?.let{ it(image) }
            }
        }
    }

private var onItemClickListener: ((Image)-> Unit)? = null

    fun setOnItemClickListener(listener : ((Image)-> Unit) ){
        onItemClickListener = listener
    }

}
