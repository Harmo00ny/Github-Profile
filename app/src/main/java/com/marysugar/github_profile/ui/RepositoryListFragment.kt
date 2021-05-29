package com.marysugar.github_profile.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentRepositoryListBinding
import com.marysugar.github_profile.model.LoadingState
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
        commonViewModel.currentFragmentTag.value = TAG
    }

    override fun onResume() {
        super.onResume()
        commonViewModel.currentFragmentTag.value = TAG
    }

    private fun setupUI() {
        val adapter = RepositoryListAdapter { repository: Repository -> repositoryClicked(repository) }
        binding.adapter = adapter

        val margin = resources.getDimensionPixelOffset(R.dimen.repository_margin)

        binding.recyclerView.addItemDecoration(
            ItemMarginDecoration(margin)
        )

        viewModel.let { vm ->
            vm.data.observe(viewLifecycleOwner, { list ->
                Log.d(TAG, list.toString())
                list.let(adapter::submitList)
            })
            vm.loading.observe(viewLifecycleOwner, { loading ->
                binding.loading = loading

                if (loading.status == LoadingState.Status.FAILED) {
                    Toast.makeText(context, loading.msg, Toast.LENGTH_LONG).show()
                }
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