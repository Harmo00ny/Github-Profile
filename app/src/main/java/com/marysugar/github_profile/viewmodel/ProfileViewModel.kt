package com.marysugar.github_profile.viewmodel

import android.view.View
import androidx.lifecycle.*
import com.marysugar.github_profile.api.GithubApi
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val githubApi: GithubApi) : ViewModel(), LifecycleObserver {
    /**
     * LiveData
     */
    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _data = MutableLiveData<User>()
    val data: LiveData<User>
        get() = _data

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    private val _cardViewVisibility = MutableLiveData<Int>()
    val cardViewVisibility: LiveData<Int>
        get() = _cardViewVisibility

    init {
        initUiState()
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingState.postValue(LoadingState.LOADING)
                val response = githubApi.user()
                if (response.isSuccessful) {
                    _data.postValue(response.body())
                    _loadingState.postValue(LoadingState.LOADED)
                    setUiStateSuccessful()
                } else {
                    _loadingState.postValue(LoadingState.error(response.message()))
                    setUiStateFails()
                }
            } catch (e: Exception) {
                _loadingState.postValue(LoadingState.error(e.message))
                setUiStateFails()
            }
        }
    }

    private fun initUiState() {
        _progressVisibility.postValue(View.VISIBLE)
        _cardViewVisibility.postValue(View.INVISIBLE)
    }

    private fun setUiStateSuccessful() {
        _progressVisibility.postValue(View.INVISIBLE)
        _cardViewVisibility.postValue(View.VISIBLE)
    }

    private fun setUiStateFails() {
        _progressVisibility.postValue(View.INVISIBLE)
    }

    companion object {
        const val TAG = "ProfileViewModel"
    }
}