package com.talkable.core.util

import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
    if (!url.isNullOrBlank())
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

@BindingAdapter("setProgressRatio")
fun setProgressRatio(view: SeekBar, studyTime: String) {
    view.progress = when (studyTime.toInt()) {
        in 1..19 -> 11
        20 -> 25
        in 21..39 -> 37
        40 -> 50
        in 41..69 -> 63
        70 -> 75
        in 71..99 -> 89
        else -> studyTime.toInt()
    }
}

@BindingAdapter("setProgressRatio")
fun setConstraintBias(view: View, studyTime: String) {
    val layoutParams = view.layoutParams as? ConstraintLayout.LayoutParams
    layoutParams?.horizontalBias = when (studyTime.toInt()) {
        in 1..19 -> 0.125F
        20 -> 0.25F
        in 21..39 -> 0.375F
        40 -> 0.5F
        in 41..69 -> 0.625F
        70 -> 0.75F
        in 71..99 -> 0.875F
        else -> studyTime.toFloat().div(100)
    }
    view.layoutParams = layoutParams
}

@BindingAdapter("setTopBottom")
fun setLayoutConstraintTopToBottomOf(view: View, targetId: Int) {
    val params = view.layoutParams as ConstraintLayout.LayoutParams
    params.topToBottom = targetId
    view.layoutParams = params
}

@BindingAdapter("formatMinutesToHoursMinutes")
fun TextView.formatMinutesToHoursMinutes(minutes: Int) {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    text = when {
        hours > 0 && remainingMinutes > 0 -> "${hours}시간 ${remainingMinutes}분"
        hours > 0 -> "${hours}시간"
        else -> "${minutes}분"
    }
}

