package com.marysugar.github_profile.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.ActivityMainBinding
import com.marysugar.github_profile.viewmodel.CommonViewModel


class MainActivity : AppCompatActivity(), RepositoryListFragment.ActivityCallback {

    private val viewModel by viewModels<CommonViewModel>()
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setFragment(ProfileFragment(), ProfileFragment.TAG)
        setEvent()
        setObserver()

        viewModel.currentFragmentTag.value = TAG
    }
    private fun setFragment(fragment: Fragment, tag: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag(tag)
        // 既に同じフラグメントが表示されている場合replaceしない
        if (currentFragment != null && currentFragment.isVisible) {
            Log.d(TAG, "Same fragment already")
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, fragment, tag)
                commit()
            }
        }
    }

    override fun onRepositoryClicked() {
        Log.d(TAG, "RepositoryClicked")
        setRepositoryDetailFragment()
    }

    private fun setRepositoryDetailFragment() {
        val fragment = RepositoryDetailFragment()
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(RepositoryDetailFragment.TAG)
            replace(R.id.container, fragment, RepositoryDetailFragment.TAG)
            commit()
        }
    }

    private fun setObserver() {
        viewModel.currentFragmentTag.observe(this, {
            when(it) {
                ProfileFragment.TAG -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    binding.toolbar.title = viewModel.toolbarTitleProfile
                    binding.bottomNavigationView.isVisible = true
                    Log.d(TAG, "ProfileFragment")
                }
                RepositoryListFragment.TAG -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    binding.toolbar.title = viewModel.toolbarTitleRepository
                    binding.bottomNavigationView.isVisible = true
                    Log.d(TAG, "RepositoryListFragment")
                }
                RepositoryDetailFragment.TAG -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding.toolbar.title = viewModel.repositoryName
                    binding.bottomNavigationView.isVisible = false
                    Log.d(TAG, "RepositoryDetailFragment")
                }
            }
        })
    }

    private fun setEvent() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.profile -> {
                    setFragment(ProfileFragment(), ProfileFragment.TAG)
                }
                R.id.repository -> {
                    setFragment(RepositoryListFragment(), RepositoryListFragment.TAG)
                }
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}