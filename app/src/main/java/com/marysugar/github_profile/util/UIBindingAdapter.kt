package com.marysugar.github_profile.util

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.marysugar.github_profile.R
import com.marysugar.github_profile.model.Languages
import com.marysugar.github_profile.model.LoadingState
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

object UIBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["setImageUrl"])
    fun ImageView.bindImageUrl(url: String?) {
        if (url != null && url.isNotBlank()) {

            Picasso.get()
                .load(url)
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("setLanguageImg")
    fun ImageView.setLanguageImg(language: String?) {
        if (language != null) {
            when (language) {
                Languages.Kotlin.toString() -> {
                    val resource = R.drawable.ic_kotlin
                    this.setImageResource(resource)
                }
                Languages.Python.toString() -> {
                    val resource = R.drawable.ic_icons8_python
                    this.setImageResource(resource)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    @BindingAdapter("convertDate")
    fun TextView.convertDate(inputDate: String?) {
        if (inputDate != null) {
            Log.d(TAG, inputDate)
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputFormat = SimpleDateFormat("yyyy/MM/dd")
            val date = inputFormat.parse(inputDate)
            val formattedDate: String = outputFormat.format(date!!)

            this.text = formattedDate
        }
    }

    @JvmStatic
    @BindingAdapter("viewVisibility")
    fun View.viewVisibility(loading: LoadingState?) {
        if (loading != null) {
            Log.d(TAG, loading.toString())
            this.visibility = when (loading.status) {
                LoadingState.Status.RUNNING -> View.INVISIBLE
                LoadingState.Status.SUCCESS -> View.VISIBLE
                LoadingState.Status.FAILED -> View.INVISIBLE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("progressBarVisibility")
    fun View.progressBarVisibility(loading: LoadingState?) {
        if (loading != null) {
            Log.d(TAG, loading.toString())
            this.visibility = when (loading.status) {
                LoadingState.Status.RUNNING -> View.VISIBLE
                LoadingState.Status.SUCCESS -> View.INVISIBLE
                LoadingState.Status.FAILED -> View.INVISIBLE
            }
        }
    }

    private const val TAG = "UIBindingAdapter"
}

