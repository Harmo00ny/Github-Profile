package com.marysugar.github_profile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryDetail(
    val created_at: String,
    val updated_at: String
): Parcelable
