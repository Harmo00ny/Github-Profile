package com.marysugar.github_profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel() {
    val toolbarTitleProfile = "Profile"
    val toolbarTitleRepository = "Repository"

    var repositoryName = ""

    val currentFragmentTag: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}