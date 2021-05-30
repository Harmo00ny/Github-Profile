package com.marysugar.github_profile.viewmodel

import androidx.lifecycle.*
import com.marysugar.github_profile.api.GithubApi
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val githubApi: GithubApi) : ViewModel(), LifecycleObserver {
    private val _data = MutableLiveData<User>()
    val data: LiveData<User>
        get() = _data

    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState>
        get() = _loading

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(LoadingState.LOADING)
                val response = githubApi.user()
                if (response.isSuccessful) {
                    _loading.postValue(LoadingState.LOADED)
                    _data.postValue(response.body())
                } else {
                    _loading.postValue(LoadingState.error(response.message()))
                }
            } catch (e: Exception) {
                _loading.postValue(LoadingState.error(e.message))
            }
        }
    }

    companion object {
        const val TAG = "ProfileViewModel"
    }
}