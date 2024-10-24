package com.talkable.presentation.challenge

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import com.talkable.databinding.DialogChallengeBinding

class ChallengeDialog(
    context: Context,
    private val challenge: Recruitment,
    private val listener: OnJoinCompleteListener
) : Dialog(context) {
    private val binding: DialogChallengeBinding =
        DialogChallengeBinding.inflate(layoutInflater)

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀바 제거
        setContentView(binding.root)
        window?.apply {
            setGravity(Gravity.CENTER) // 다이얼로그 위치
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 테두리 없애기
        }
        initSetTextAndImage()
        initJoinBtnClickListener()
        initCloseBtnClickListener()
    }

    // 텍스트와 이미지 적용
    private fun initSetTextAndImage() {
        with(binding) {
            tvChallengeDate.text = challenge.date
            tvChallengeTitle.text = challenge.title
            ivChallengeImage.setImageResource(challenge.image)
        }
    }

    // 참여하기 버튼
    private fun initJoinBtnClickListener() {
        with(binding) {
            btnChallengeJoin.setOnClickListener {
                listener.onJoinComplete()
                dismiss()
            }
        }
    }

    // 닫기 버튼
    private fun initCloseBtnClickListener() {
        with(binding) {
            btnChallengeClose.setOnClickListener {
                dismiss()
            }
        }
    }

    interface OnJoinCompleteListener {
        fun onJoinComplete()
    }
}