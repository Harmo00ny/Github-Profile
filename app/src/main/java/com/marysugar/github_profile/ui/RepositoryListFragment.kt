package com.marysugar.github_profile.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentRepositoryListBinding
import com.marysugar.github_profile.model.Repository
import com.marysugar.github_profile.ui.adapter.RepositoryListAdapter
import com.marysugar.github_profile.util.ItemMarginDecoration
import com.marysugar.github_profile.viewmodel.CommonViewModel
import com.marysugar.github_profile.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ClassCastException


class RepositoryListFragment : Fragment() {
    private val commonViewModel by activityViewModels<CommonViewModel>()
    private val viewModel: RepositoryViewModel by viewModel()
    private lateinit var binding: FragmentRepositoryListBinding

    private lateinit var callback: ActivityCallback

    interface ActivityCallback {
        fun onRepositoryClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = context as ActivityCallback
        } catch (e: ClassCastException) {
            Log.e(TAG, e.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonViewModel.currentFragmentTag.value = TAG
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onResume() {
        super.onResume()
        commonViewModel.currentFragmentTag.value = TAG
    }

    private fun setupUI() {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        commonViewModel.repositoryName = repository.name
        callback.onRepositoryClicked()
    }

    companion object {
        const val TAG = "RepositoryListFragment"
    }
}