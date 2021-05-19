package com.marysugar.github_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentProfileBinding
import com.marysugar.github_profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel by viewModel<ProfileViewModel>()
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
        profileViewModel.let {
            it.data.observe(this , { user ->
                binding.user = user
            })
            it.progressVisibility.observe(this, { visibility ->
                binding.progressBar.visibility = visibility
            })
        }
    }

    companion object {
        const val TAG = "ProfileFragment"
    }
}