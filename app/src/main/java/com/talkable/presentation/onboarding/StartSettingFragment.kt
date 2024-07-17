package com.talkable.presentation.onboarding

import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.colorOf
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentStartSettingBinding
import com.talkable.presentation.onboarding.model.User

class StartSettingFragment :
    BindingFragment<FragmentStartSettingBinding>(R.layout.fragment_start_setting) {
    override fun initView() {
        statusBarColorOf(R.color.main_3)
        setNameTextColor()
        handleViewVisibilityDelay()
    }

    private fun setNameTextColor() {
        binding.tvStartSettingTitle.text = getSpannableText(mockData.name)
    }

    private fun getSpannableText(
        name: String,
    ): SpannableStringBuilder {
        val spannableText =
            SpannableStringBuilder(name + binding.tvStartSettingTitle.text.toString())

        spannableText.setSpan(
            ForegroundColorSpan(colorOf(R.color.gray)),
            0,
            name.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableText
    }

    private fun handleViewVisibilityDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            setWelcomeTextViewVisibility(false)
            setFlowerSettingViewVisibility(true)
        }, 2500)
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToAlarmSettingFragment()
        }, 5000)
    }

    private fun setWelcomeTextViewVisibility(isVisible: Boolean) = with(binding) {
        tvStartSettingTitle.isVisible = isVisible
        ivStartSettingLogo.isVisible = isVisible
    }

    private fun setFlowerSettingViewVisibility(isVisible: Boolean) = with(binding) {
        tvStartSettingFlowerTitle.isVisible = isVisible
        ivStartSettingSeed.isVisible = isVisible
        tvStartSettingFlowerDescription.isVisible = isVisible
    }

    private fun navigateToAlarmSettingFragment() =
        findNavController().navigate(R.id.action_startSetting_to_alarmSetting)

    companion object {
        val mockData = User(
            name = "박소현"
        )
    }
}