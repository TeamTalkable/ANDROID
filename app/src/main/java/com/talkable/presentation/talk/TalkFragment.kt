package com.talkable.presentation.talk

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View.GONE
import androidx.core.view.isVisible
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentTalkBinding
import com.talkable.presentation.MainActivity
import kotlin.random.Random

class TalkFragment : BindingFragment<FragmentTalkBinding>(R.layout.fragment_talk) {
    override fun initView() {
        (activity as? MainActivity)?.hideBottomNavigation()
        initGuideLayoutClickListener()
        setRandomVideo()
        initTranslateBtnClickListener()
        initShowBtnClickListener()
    }

    // 코치 마크 레이아웃
    private fun initGuideLayoutClickListener() {
        val layoutGuide = binding.includeLayoutTalkGuide.layoutTalkGuide
        layoutGuide.setOnClickListener {
            layoutGuide.visibility = GONE
        }
    }

    // 비디오 랜덤 적용
    private fun setRandomVideo() {
        val videoResources = arrayOf(
            R.raw.bg_talk_school,
            R.raw.bg_talk_classroom,
            R.raw.bg_talk_library
        )

        val randomVideoResource = videoResources[Random.nextInt(videoResources.size)]
        val videoPath = "android.resource://${requireContext().packageName}/$randomVideoResource"
        val videoUri = Uri.parse(videoPath)

        binding.videoViewTalkBackground.setVideoURI(videoUri)
        binding.videoViewTalkBackground.start()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.videoViewTalkBackground.pause()
        }, 1000)
    }

    // 번역 버튼 클릭
    private fun initTranslateBtnClickListener() {
        with(binding) {
            btnTalkTranslate.setOnClickListener {
                btnTalkTranslate.isSelected = !binding.btnTalkTranslate.isSelected
                tvTalkTranslate.isVisible = !binding.tvTalkTranslate.isVisible
            }
        }
    }

    // 보기 버튼 클릭
    private fun initShowBtnClickListener() {
        with(binding) {
            btnTalkShow.setOnClickListener {
                btnTalkShow.isSelected = !binding.btnTalkShow.isSelected
                tvTalkText.isVisible = !binding.tvTalkText.isVisible
                tvTalkListen.isVisible = !binding.tvTalkListen.isVisible
            }
        }
    }
}