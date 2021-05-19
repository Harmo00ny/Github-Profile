package com.marysugar.github_profile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.marysugar.github_profile.viewmodel.MainViewModel
import com.marysugar.github_profile.R
import com.marysugar.github_profile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setCurrentFragment(ProfileFragment())

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.profile -> {
                    setCurrentFragment(ProfileFragment())
                    binding.toolbar.title = mainViewModel.toolbarTitleProfile
                }
                R.id.repository -> {
                    setCurrentFragment(RepositoryFragment())
                    binding.toolbar.title = mainViewModel.toolbarTitleRepository
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
}