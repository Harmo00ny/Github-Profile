package com.marysugar.github_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentRepositoryDetailBinding
import com.marysugar.github_profile.viewmodel.CommonViewModel
import com.marysugar.github_profile.viewmodel.RepositoryDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RepositoryDetailFragment : Fragment() {
    private val commonViewModel by activityViewModels<CommonViewModel>()
    private val viewModel: RepositoryDetailViewModel by viewModel()
    private lateinit var binding: FragmentRepositoryDetailBinding

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
            R.layout.fragment_repository_detail,
            container,
            false
        )

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setupRepositoryDetail()
    }

    private fun setupRepositoryDetail() {
        viewModel.fetchRepositoryDetail(commonViewModel.repositoryName)
        viewModel.let { vm ->
            vm.repositoryDetail.observe(this, {
                binding.repositoryDetail = it
            })

            vm.layoutVisibility.observe(this, {
                binding.layout.visibility = it
            })
            vm.progressVisibility.observe(this, {
                binding.progressBar.visibility = it
            })
            vm.readme.observe(this, {
                binding.tvDescription.text = it
            })
        }
    }

    companion object {
        const val TAG = "RepositoryDetailFragment"
    }
}