package com.talkable.core.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import com.talkable.core.view.ChartView
import com.talkable.presentation.mypage.model.Chart


@BindingAdapter("imageUrl")
fun loadImage(
    view: ImageView,
    url: String?,
) {
    view.load(url)
}

@BindingAdapter("setCircleImage")
fun ImageView.setCircleImage(img: String?) {
    load(img) {
        transformations(RoundedCornersTransformation(1000f))
    }
}

@BindingAdapter("setChartPercentage")
fun setChartPercentage(view: ChartView, percentage: Chart) {
    view.setChartPercentage(percentage)
}

@BindingAdapter("tint")
fun setTint(view: ImageView, color: Int) {
    if (color != 0) {
        view.setColorFilter(color)
    } else {
        view.clearColorFilter()
    }
}