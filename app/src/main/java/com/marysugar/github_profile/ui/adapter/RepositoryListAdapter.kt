package com.marysugar.github_profile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.marysugar.github_profile.databinding.ItemRepositoryBinding
import com.marysugar.github_profile.model.Repository

class RepositoryListAdapter
    : ListAdapter<Repository, RepositoryListAdapter.RepositoryViewHolder>(Companion) {

    class RepositoryViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(
            oldItem: Repository,
            newItem: Repository): Boolean = oldItem === newItem
        override fun areContentsTheSame(
            oldItem: Repository,
            newItem: Repository): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoryBinding.inflate(layoutInflater)

        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentRepository = getItem(position)
        holder.binding.repository = currentRepository
        holder.binding.executePendingBindings()
    }
}