package com.marysugar.github_profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel() {
    val toolbarTitleProfile = "Profile"
    val toolbarTitleRepository = "Repository"
    val currentFragmentTag: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val repositoryName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun setRepositoryName(name: String) {
        repositoryName.value = name
    }

}