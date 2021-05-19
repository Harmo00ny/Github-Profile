package com.marysugar.github_profile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val avatar_url: String,
    val login: String
) : Parcelable
