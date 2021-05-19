package com.marysugar.github_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentRepositoryBinding
import com.marysugar.github_profile.ui.adapter.RepositoryListAdapter
import com.marysugar.github_profile.viewmodel.RepositoryListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryListFragment : Fragment() {
    private val repositoryListViewModel: RepositoryListViewModel by viewModel()
    private lateinit var binding: FragmentRepositoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repository,
            container,
            false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupAdapter()
    }

    private fun setupAdapter() {
        val repositoryListAdapter = RepositoryListAdapter()
        binding.adapter = repositoryListAdapter

        repositoryListViewModel.data.observe(this, {
            it.let(repositoryListAdapter::submitList)
        })
    }

    companion object {
        const val TAG = "RepositoryListFragment"
    }
}