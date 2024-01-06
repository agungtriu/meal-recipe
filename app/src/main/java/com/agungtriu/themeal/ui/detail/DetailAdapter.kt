package com.agungtriu.themeal.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agungtriu.themeal.R
import com.agungtriu.themeal.databinding.ItemDetailBinding
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class DetailAdapter(private val lifecycle: Lifecycle) :
    ListAdapter<String, DetailAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    inner class ViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            if (position == currentList.size - 1) {
                binding.ypItemdetail.visibility = View.VISIBLE
                lifecycle.addObserver(binding.ypItemdetail)
                binding.ypItemdetail.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = item.split("=")[1]
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            } else {
                binding.ivItemdetail.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(item)
                    .placeholder(R.mipmap.ic_thumbnail)
                    .centerCrop()
                    .into(binding.ivItemdetail)
            }
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }
}