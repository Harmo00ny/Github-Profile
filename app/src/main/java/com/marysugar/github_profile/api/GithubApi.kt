package com.marysugar.github_profile.api

import com.marysugar.github_profile.model.Repository
import com.marysugar.github_profile.model.RepositoryDetail
import com.marysugar.github_profile.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    //cccaaa0
    @GET("users/octocat")
    suspend fun user(): Response<User>

    @GET("users/octocat/repos")
    suspend fun repos(): Response<List<Repository>>

    @GET("repos/octocat/{repository}")
    suspend fun repo(@Path("repository") repository: String?): Response<RepositoryDetail>
}