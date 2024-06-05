package com.talkable.presentation.talk

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.talkable.R
import com.talkable.databinding.ToastTalkHintBinding

object HintToast {
    fun createToast(context: Context, title: String, message: String): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastTalkHintBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_talk_hint, null, false)

        binding.tvToastTalkTitle.text = title
        binding.tvToastTalkContent.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 50.toPx())
            duration = Toast.LENGTH_LONG
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}