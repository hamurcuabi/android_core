package com.emrhmrc.mvvmcore.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emrhmrc.mvvmcore.data.network.model.ApiUser
import com.emrhmrc.mvvmcore.databinding.ItemLayoutBinding

class UserAdapter(private val listener: (ApiUser) -> Unit) :
    ListAdapter<ApiUser, UserAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ViewHolder(
        private val binding: ItemLayoutBinding,
        private val listener: (ApiUser) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userEntity: ApiUser) {
            itemView.setOnClickListener { listener.invoke(userEntity) }

            binding.apply {
                txtName.text = userEntity.name
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ApiUser>() {
        override fun areItemsTheSame(oldItem: ApiUser, newItem: ApiUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ApiUser, newItem: ApiUser) =
            oldItem == newItem
    }
}