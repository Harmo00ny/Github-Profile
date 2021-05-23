package com.marysugar.github_profile.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marysugar.github_profile.api.GithubApi
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryViewModel(private val githubApi: GithubApi) : ViewModel() {
    /**
     * LiveData
     */
    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState>
        get() = _loading

    private val _data = MutableLiveData<List<Repository>>()
    val data: LiveData<List<Repository>>
        get() = _data

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    init {
        fetchData()
        initUiState()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(LoadingState.LOADING)
                val response = githubApi.repos()
                if (response.isSuccessful) {
                    _data.postValue(response.body())
                    _loading.postValue(LoadingState.LOADED)

                    setUiStateSuccessful()
                } else {
                    _loading.postValue(LoadingState.error(response.message()))

                    setUiStateFails()
                }

            } catch (e: Exception) {
                _loading.postValue(LoadingState.error(e.message))

                setUiStateFails()
            }
        }
    }

    private fun initUiState() {
        _progressVisibility.postValue(View.VISIBLE)
    }

    private fun setUiStateSuccessful() {
        _progressVisibility.postValue(View.INVISIBLE)
    }

    private fun setUiStateFails() {
        _progressVisibility.postValue(View.INVISIBLE)
    }
}