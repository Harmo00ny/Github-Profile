package com.marysugar.github_profile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val id: Long,
    val name: String,
    val language: String
) : Parcelable