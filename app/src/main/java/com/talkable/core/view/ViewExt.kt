package com.talkable.core.view

import android.view.View

fun View.visible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.toggleSelected() {
    this.isSelected = !this.isSelected
}