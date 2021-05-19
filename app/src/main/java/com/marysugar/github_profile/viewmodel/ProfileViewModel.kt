package com.marysugar.github_profile.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marysugar.github_profile.api.GithubApi
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val githubApi: GithubApi) : ViewModel() {
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
        fetchProfile()
        _progressVisibility.postValue(View.INVISIBLE)
        _cardViewVisibility.postValue(View.INVISIBLE)
    }

    private fun fetchProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fetchProfileResponse()
            } catch (e: Exception) {
                _loadingState.postValue(LoadingState.error(e.message))
            }
        }
    }

    private suspend fun fetchProfileResponse() {
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
    }

    private fun setUiStateSuccessful() {
        _progressVisibility.postValue(View.INVISIBLE)
        _cardViewVisibility.postValue(View.VISIBLE)
    }

    private fun setUiStateFails() {
        _progressVisibility.postValue(View.INVISIBLE)
    }
}