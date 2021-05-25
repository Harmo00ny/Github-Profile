package com.marysugar.github_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentRepositoryDetailBinding
import com.marysugar.github_profile.viewmodel.CommonViewModel
import com.marysugar.github_profile.viewmodel.RepositoryDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryDetailFragment : Fragment() {
    private val commonViewModel: CommonViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )
    private val viewModel: RepositoryDetailViewModel by viewModel()
    private lateinit var binding: FragmentRepositoryDetailBinding

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
        viewModel.let {
            it.repositoryDetail.observe(this, { detail ->
                binding.repositoryDetail = detail
//                binding.parent.visibility = View.VISIBLE
            })
        }
    }

    companion object {
        const val TAG = "RepositoryDetailFragment"
    }
}