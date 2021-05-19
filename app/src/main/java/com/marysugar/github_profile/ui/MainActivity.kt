package com.marysugar.github_profile.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.ActivityMainBinding
import com.marysugar.github_profile.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setCurrentFragment(ProfileFragment(), ProfileFragment.TAG)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.profile -> {
                    setCurrentFragment(ProfileFragment(), ProfileFragment.TAG)
                    binding.toolbar.title = mainViewModel.toolbarTitleProfile
                }
                R.id.repository -> {
                    setCurrentFragment(RepositoryFragment(), RepositoryFragment.TAG)
                    binding.toolbar.title = mainViewModel.toolbarTitleRepository
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment, tag: String) {
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

    companion object {
        const val TAG = "MainActivity"
    }
}