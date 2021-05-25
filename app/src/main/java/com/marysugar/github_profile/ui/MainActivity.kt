package com.marysugar.github_profile.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.ActivityMainBinding
import com.marysugar.github_profile.viewmodel.CommonViewModel
import java.lang.ref.WeakReference


class MainActivity : AppCompatActivity() {

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
    }
    private fun setFragment(fragment: Fragment, tag: String) {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
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

    private fun setEvent() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.profile -> {
                    setFragment(ProfileFragment(), ProfileFragment.TAG)
                    binding.toolbar.title = viewModel.toolbarTitleProfile
                }
                R.id.repository -> {
                    setFragment(RepositoryListFragment(), RepositoryListFragment.TAG)
                    binding.toolbar.title = viewModel.toolbarTitleRepository
                }
            }
            true
        }

        binding.toolbar.setNavigationOnClickListener {
            changeAppearanceToolbar()
            onBackPressed()
        }
    }

    fun setRepositoryDetailFragment(fragment: Fragment, tag: String) {
        binding.toolbar.title = viewModel.repositoryName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(tag)
            replace(R.id.container, fragment, tag)
            commit()
        }
    }

    fun changeAppearanceToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.toolbar.title = viewModel.toolbarTitleRepository
    }

    companion object {
        const val TAG = "MainActivity"
    }
}