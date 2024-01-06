package com.agungtriu.themeal.ui.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agungtriu.themeal.R
import com.agungtriu.themeal.databinding.ItemMealBinding
import com.agungtriu.themeal.ui.detail.DetailFragment.Companion.ID_KEY
import com.agungtriu.themeal.utils.MealItem
import com.bumptech.glide.Glide

class BookmarkAdapter(
    private val viewModel: BookmarkViewModel,
    private val findNavController: NavController
) :
    ListAdapter<MealItem, BookmarkAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealItem) {
            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.mipmap.ic_thumbnail)
                .into(binding.ivItemmeal)
            binding.tvItemmeal.text = item.title
            binding.ivItemmealBookmark.visibility = View.VISIBLE
            binding.ivItemmealBookmark.setColorFilter(ContextCompat.getColor(itemView.context, R.color.black))
            binding.ivItemmealBookmark.setOnClickListener {
                viewModel.deleteMeals(item.id ?: "")
            }

            binding.cardItemmeal.setOnClickListener {
                findNavController.navigate(
                    R.id.action_bookmarkFragment_to_detailFragment,
                    bundleOf(ID_KEY to item.id)
                )
            }
        }
    }

    companion object {
        val callback = object : DiffUtil.ItemCallback<MealItem>() {
            override fun areItemsTheSame(oldItem: MealItem, newItem: MealItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MealItem, newItem: MealItem): Boolean =
                oldItem.id == newItem.id

        }
    }

}