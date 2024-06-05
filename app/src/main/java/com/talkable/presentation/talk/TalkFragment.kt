package com.talkable.presentation.talk

import android.view.View.GONE
import androidx.core.view.isVisible
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentTalkBinding
import com.talkable.presentation.MainActivity

class TalkFragment : BindingFragment<FragmentTalkBinding>(R.layout.fragment_talk) {
    override fun initView() {
        (activity as? MainActivity)?.hideBottomNavigation()
        initGuideLayoutClickListener()
        initTranslateBtnClickListener()
    }

    // 코치 마크 레이아웃
    private fun initGuideLayoutClickListener() {
        val layoutGuide = binding.includeLayoutTalkGuide.layoutTalkGuide
        layoutGuide.setOnClickListener {
            layoutGuide.visibility = GONE
        }
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
}