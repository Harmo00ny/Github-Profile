package com.marysugar.github_profile.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
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
}

