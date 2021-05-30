package com.marysugar.github_profile.viewmodel

import android.util.Log
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

    private val _data = MutableLiveData<RepositoryDetail>()
    val data: LiveData<RepositoryDetail>
        get() = _data

    private val _readme = MutableLiveData<String>()
    val readme: LiveData<String>
        get() = _readme

    fun fetchData(repositoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // オプショナルとしてReadme.mdを取得
            fetchReadme(repositoryName)
            fetchDetail(repositoryName)
        }
    }

    private suspend fun fetchDetail(repositoryName: String) {
        try {
            _loading.postValue(LoadingState.LOADING)
            val response = githubApi.repo(repositoryName)
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

    companion object {
        const val TAG = "RepositoryDetailVM"
    }
}