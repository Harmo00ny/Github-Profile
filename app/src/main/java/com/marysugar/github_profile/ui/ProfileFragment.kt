package com.marysugar.github_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentProfileBinding
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.viewmodel.CommonViewModel
import com.marysugar.github_profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val commonViewModel by activityViewModels<CommonViewModel>()
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
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

    private fun setupUI() {
        viewModel.let { vm ->
            vm.data.observe(this, {
                binding.user = it
            })
            vm.loading.observe(this, {
                binding.loading = it
                // 通信がFailedのときはエラーメッセージを表示
                if (it.status == LoadingState.Status.FAILED) {
                    Toast.makeText(context, it.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    companion object {
        const val TAG = "ProfileFragment"
    }
}