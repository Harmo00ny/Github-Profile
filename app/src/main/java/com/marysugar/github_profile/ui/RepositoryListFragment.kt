package com.marysugar.github_profile.ui

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
import com.marysugar.github_profile.ui.adapter.RepositoryListAdapter
import com.marysugar.github_profile.util.ItemMarginDecoration
import com.marysugar.github_profile.viewmodel.CommonViewModel
import com.marysugar.github_profile.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryListFragment : Fragment() {
    private val commonViewModel by activityViewModels<CommonViewModel>()
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
        commonViewModel.currentFragmentTag.value = TAG
    }

    override fun onResume() {
        super.onResume()
        commonViewModel.currentFragmentTag.value = TAG
    }

    private fun setupUI() {
        val adapter = RepositoryListAdapter()
        adapter.setOnItemClickListener {
            Log.d(TAG,it.name)
            commonViewModel.setRepositoryName(it.name)
        }

        binding.adapter = adapter

        val margin = resources.getDimensionPixelOffset(R.dimen.repository_margin)

        binding.recyclerView.addItemDecoration(
            ItemMarginDecoration(margin)
        )

        viewModel.let { vm ->
            vm.data.observe(viewLifecycleOwner, {
                Log.d(TAG, it.toString())
                it.let(adapter::submitList)
            })
            vm.loading.observe(viewLifecycleOwner, {
                binding.loading = it

                if (it.status == LoadingState.Status.FAILED) {
                    Toast.makeText(context, it.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    companion object {
        const val TAG = "RepositoryListFragment"
    }
}