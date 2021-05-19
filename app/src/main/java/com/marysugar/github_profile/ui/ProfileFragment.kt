package com.marysugar.github_profile.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.FragmentProfileBinding
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by sharedViewModel()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(profileViewModel)
        Log.d(TAG,"fetch")
    }

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
            it.data.observe(this, { user ->
                binding.user = user
            })
            it.progressVisibility.observe(this, { visibility ->
                binding.progressBar.visibility = visibility
            })
            it.cardViewVisibility.observe(this, { visibility ->
                binding.cardView.visibility = visibility
            })
            it.loadingState.observe(this, { loadingState ->
                if (loadingState.status.name == LoadingState.Status.FAILED.toString()) {
                    Toast.makeText(context,loadingState.msg, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    companion object {
        const val TAG = "ProfileFragment"
    }
}