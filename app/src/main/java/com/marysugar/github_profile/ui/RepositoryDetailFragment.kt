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
import com.marysugar.github_profile.databinding.FragmentRepositoryDetailBinding
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.viewmodel.CommonViewModel
import com.marysugar.github_profile.viewmodel.RepositoryDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryDetailFragment(private val repositoryName: String) : Fragment() {
    private val commonViewModel by activityViewModels<CommonViewModel>()
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
        commonViewModel.currentFragmentTag.value = TAG
    }

    private fun setupRepositoryDetail() {
        viewModel.fetchData(repositoryName)
        viewModel.let { vm ->
            vm.data.observe(this, {
                Log.d(TAG, it.toString())
                binding.data = it
            })

            vm.loading.observe(this, {
                binding.loading = it

                if (it.status == LoadingState.Status.FAILED) {
                    Toast.makeText(context, it.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    companion object {
        const val TAG = "RepositoryDetailFragment"
    }
}