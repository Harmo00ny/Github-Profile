package com.marysugar.github_profile.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentRepositoryListBinding
import com.marysugar.github_profile.model.Repository
import com.marysugar.github_profile.ui.adapter.RepositoryListAdapter
import com.marysugar.github_profile.util.ItemMarginDecoration
import com.marysugar.github_profile.viewmodel.CommonViewModel
import com.marysugar.github_profile.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryListFragment : Fragment() {
    private val commonViewModel: CommonViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )
    private val viewModel: RepositoryViewModel by viewModel()
    private lateinit var binding: FragmentRepositoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repository_list,
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
        val repositoryListAdapter = RepositoryListAdapter {
                repository: Repository -> repositoryClicked(repository)
        }
        val itemMargin = resources.getDimensionPixelOffset(R.dimen.repository_margin)

        binding.recyclerView.addItemDecoration(
            ItemMarginDecoration(itemMargin)
        )
        binding.adapter = repositoryListAdapter

        viewModel.let {
            it.data.observe(this, { list ->
                Log.d(TAG, list.toString())
                list.let(repositoryListAdapter::submitList)
            })
            it.progressVisibility.observe(this, { visibility ->
                binding.progressBar.visibility = visibility
            })
        }
    }

    private fun repositoryClicked(repository : Repository) {
        Log.d(TAG, repository.id.toString())
        commonViewModel.repositoryName = repository.name
        (activity as MainActivity).setContentFragment(RepositoryDetailFragment(), RepositoryDetailFragment.TAG)
    }

    companion object {
        const val TAG = "RepositoryListFragment"
    }
}