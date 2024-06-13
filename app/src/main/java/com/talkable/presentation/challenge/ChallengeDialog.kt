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
    challengeDate: String,
    challengeTitle: String
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
        initSetText(challengeDate, challengeTitle)
        initJoinBtnClickListener()
        initCloseBtnClickListener()
    }

    // 텍스트 적용
    private fun initSetText(challengeDate: String, challengeTitle: String) {
        with(binding) {
            tvChallengeDate.text = challengeDate
            tvChallengeTitle.text = challengeTitle
        }
    }

    // 참여하기 버튼
    private fun initJoinBtnClickListener() {
        with(binding) {
            btnChallengeJoin.setOnClickListener {
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
}