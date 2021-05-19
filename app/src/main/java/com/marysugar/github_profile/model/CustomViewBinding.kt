package com.marysugar.github_profile.model

import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["setupVisibility"])
fun ProgressBar.progressVisibility(loadingState: LoadingState?) {
    loadingState?.let {
        isVisible = when(it.status) {
            LoadingState.Status.RUNNING -> true
            LoadingState.Status.SUCCESS -> false
            LoadingState.Status.FAILED -> false
        }
    }
}