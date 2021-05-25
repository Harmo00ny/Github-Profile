package com.marysugar.github_profile.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marysugar.github_profile.api.GithubApi
import com.marysugar.github_profile.model.LoadingState
import com.marysugar.github_profile.model.RepositoryDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryDetailViewModel(private val githubApi: GithubApi) : ViewModel() {

    private val _loading = MutableLiveData<LoadingState>()
    val loading: LiveData<LoadingState>
        get() = _loading

    private val _repositoryDetail = MutableLiveData<RepositoryDetail>()
    val repositoryDetail: LiveData<RepositoryDetail>
        get() = _repositoryDetail

    private val _layoutVisibility = MutableLiveData<Int>()
    val layoutVisibility: LiveData<Int>
        get() = _layoutVisibility

    private val _progressVisibility = MutableLiveData<Int>()
    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    init {
        initUiState()
    }

    fun fetchRepositoryDetail(repositoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(LoadingState.LOADING)
                val response = githubApi.repo(repositoryName)
                if (response.isSuccessful) {
                    _repositoryDetail.postValue(response.body())
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
        _layoutVisibility.postValue(View.INVISIBLE)
        _progressVisibility.postValue(View.VISIBLE)
    }

    private fun setUiStateSuccessful() {
        _layoutVisibility.postValue(View.VISIBLE)
        _progressVisibility.postValue(View.INVISIBLE)
    }

    private fun setUiStateFails() {
        _layoutVisibility.postValue(View.INVISIBLE)
        _progressVisibility.postValue(View.INVISIBLE)
    }
}