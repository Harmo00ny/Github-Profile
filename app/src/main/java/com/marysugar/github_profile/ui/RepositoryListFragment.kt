package com.marysugar.github_profile.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentRepositoryBinding
import com.marysugar.github_profile.ui.adapter.RepositoryListAdapter
import com.marysugar.github_profile.util.ItemMarginDecoration
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
        setupUI()
    }

    private fun setupUI() {
        val repositoryListAdapter = RepositoryListAdapter()
        val itemMargin = resources.getDimensionPixelOffset(R.dimen.repository_margin)

        binding.recyclerView.addItemDecoration(
            ItemMarginDecoration(itemMargin)
        )
        binding.adapter = repositoryListAdapter

        repositoryListViewModel.let {
            it.data.observe(this, { list ->
                Log.d(TAG, list.toString())
                list.let(repositoryListAdapter::submitList)
            })
            it.progressVisibility.observe(this, { visibility ->
                binding.progressBar.visibility = visibility
            })
        }
    }

    companion object {
        const val TAG = "RepositoryListFragment"
    }
}