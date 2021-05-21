package com.marysugar.github_profile.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemMarginDecoration (private val itemMargin: Int) : RecyclerView.ItemDecoration() {
    private fun isLastItem(parent: RecyclerView, view: View, state: RecyclerView.State): Boolean {
        return parent.getChildAdapterPosition(view) == state.itemCount - 1
    }
}