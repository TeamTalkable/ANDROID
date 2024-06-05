package com.talkable.presentation.talk

import android.view.View.GONE
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentTalkBinding
import com.talkable.presentation.MainActivity

class TalkFragment : BindingFragment<FragmentTalkBinding>(R.layout.fragment_talk) {
    override fun initView() {
        (activity as? MainActivity)?.hideBottomNavigation()
        initGuideLayoutClickListener()
    }

    // 코치 마크 레이아웃
    private fun initGuideLayoutClickListener() {
        val layoutGuide = binding.includeLayoutTalkGuide.layoutTalkGuide
        layoutGuide.setOnClickListener {
            layoutGuide.visibility = GONE
        }
    }
}