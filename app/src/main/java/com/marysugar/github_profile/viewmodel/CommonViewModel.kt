package com.marysugar.github_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel() {
    val toolbarTitleProfile = "Profile"
    val toolbarTitleRepository = "Repository"
    val currentFragmentTag: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val _repositoryName = MutableLiveData<String>()
    val repositoryName: LiveData<String>
        get() = _repositoryName

    fun setRepositoryName(name: String) {
        _repositoryName.value = name
    }

}