package com.marysugar.github_profile.viewmodel

import android.util.Log
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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

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

    private val _readme = MutableLiveData<String>()
    val readme: LiveData<String>
        get() = _readme

    init {
        initUiState()
    }

    fun fetchRepositoryDetail(repositoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // オプショナルとしてReadme.mdを取得
            fetchReadme(repositoryName)
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

    private fun fetchReadme(repositoryName: String) {
        try {
            val document: Document =
                Jsoup.connect("https://raw.githubusercontent.com/cccaaa0/${repositoryName}/master/README.md")
                    .get()
            Log.d(TAG, document.body().html())
            _readme.postValue(document.body().html())
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
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

    companion object {
        const val TAG = "RepositoryDetailVM"
    }
}