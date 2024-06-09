package com.talkable.presentation.talk

import android.graphics.PorterDuff
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
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
        initBottomSheet()
        initListenBtnClickListener()
        initTranslateBtnClickListener()
        initShowBtnClickListener()
        initHintTextViewClickListener()
        initTalkAdapter()
    }

    // 어댑터 연결
    private fun initTalkAdapter() {
        binding.layoutBottomSheetTalk.rvBottomSheet.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TalkAdapter().apply {
                submitList(talkMockData.toMutableList())
            }
        }
    }

    // 코치 마크 레이아웃
    private fun initGuideLayoutClickListener() {
        val layoutGuide = binding.includeLayoutTalkGuide.layoutTalkGuide
        layoutGuide.setOnClickListener {
            layoutGuide.visibility = GONE
        }
    }

    // 바텀 시트
    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.layoutBottomSheetTalk.root)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    // 하단 상태일 때 버튼 숨김
                    STATE_COLLAPSED -> {
                        val customColor =
                            ContextCompat.getColor(requireContext(), R.color.font_4)
                        with(binding.layoutBottomSheetTalk) {
                            btnBottomSheetSelectArea.visibility = GONE
                            tvBottomSheetShow.setTextColor(customColor)
                            ivBottomSheetDragHandle.setColorFilter(
                                customColor, PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                    // 바텀 시트가 완전히 펼쳐졌을 때 버튼 보이게
                    STATE_EXPANDED -> {
                        val customColor =
                            ContextCompat.getColor(requireContext(), R.color.font)
                        with(binding.layoutBottomSheetTalk) {
                            btnBottomSheetSelectArea.visibility = VISIBLE
                            tvBottomSheetShow.setTextColor(customColor)
                            ivBottomSheetDragHandle.setColorFilter(
                                customColor, PorterDuff.Mode.SRC_IN
                            )
                        }
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    // 듣기 버튼 클릭
    private fun initListenBtnClickListener() {
        with(binding) {
            btnTalkListen.setOnClickListener {
                binding.videoViewTalkBackground.start()
            }
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
        }, 500)
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

    // 힌트 클릭
    private fun initHintTextViewClickListener() {
        var clickCount = FIRST_CLICK
        binding.tvTalkHint.setOnClickListener {
            when (clickCount) {
                FIRST_CLICK -> {
                    HintToast.createToast(
                        requireActivity(),
                        getString(R.string.hint_talk),
                        getString(R.string.tv_talk_content_hint)
                    )?.show()
                    changeHintText()
                }

                else -> {
                    HintToast.createToast(
                        requireActivity(),
                        getString(R.string.hint_talk_example),
                        getString(R.string.tv_talk_content_example)
                    )?.show()
                }
            }
            clickCount++
        }
    }

    //3초 뒤 텍스트 변경
    private fun changeHintText() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.tvTalkHint.text = getString(R.string.hint_talk_example)
        }, 3000)
    }

    companion object {
        const val FIRST_CLICK = 0

        // 더미 데이터
        val talkMockData = listOf(
            TalkData(type = "ai", message = "What did you do today in scool?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I learn about photosynthesis."),
            TalkData(type = "ai", message = "What did you do today in scool?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I learn about photosynthesis."),
            TalkData(type = "ai", message = "What did you do today in scool?"),
            TalkData(type = "user", message = "I took a science class today."),
            TalkData(type = "ai", message = "What did you learn today?"),
            TalkData(type = "user", message = "I learn about photosynthesis."),
        )
    }
}