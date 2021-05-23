package com.marysugar.github_profile.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.marysugar.github_profile.R
import com.marysugar.github_profile.model.Languages
import com.squareup.picasso.Picasso

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
    fun ImageView.setLanguageImg(language: String) {
        when (language) {
            Languages.Kotlin.toString()-> {
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

