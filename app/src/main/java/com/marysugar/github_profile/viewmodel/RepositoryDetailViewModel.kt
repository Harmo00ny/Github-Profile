package com.marysugar.github_profile.viewmodel

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

    fun fetchRepositoryDetail(repositoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(LoadingState.LOADING)
                val response = githubApi.repo(repositoryName)
                if (response.isSuccessful) {
                    _repositoryDetail.postValue(response.body())
                    _loading.postValue(LoadingState.LOADED)

//                    setUiStateSuccessful()
                } else {
                    _loading.postValue(LoadingState.error(response.message()))

//                    setUiStateFails()
                }

            } catch (e: Exception) {
                _loading.postValue(LoadingState.error(e.message))

//                setUiStateFails()
            }
        }
    }
}