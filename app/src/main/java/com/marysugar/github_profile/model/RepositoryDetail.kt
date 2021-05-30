package com.marysugar.github_profile.model

import com.google.gson.annotations.SerializedName

data class RepositoryDetail(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
