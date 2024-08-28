package com.talkable.presentation.talk

import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.core.view.isVisible
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.FragmentTalkFeedbackDialogBinding

class TalkFeedbackDialog() :
    BindingDialogFragment<FragmentTalkFeedbackDialogBinding>(
        R.layout.fragment_talk_feedback_dialog
    ) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            attributes.y = 600

            addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun initView() {
        initListenBtnClickListener()
        initTranslateBtnClickListener()
        initCloseBtnClickListener()
    }

    private fun initListenBtnClickListener() {
        with(binding) {
            btnFeedbackTalkListen.setOnClickListener {
                btnFeedbackTalkListen.isSelected = !binding.btnFeedbackTalkListen.isSelected
            }
        }
    }

    private fun initTranslateBtnClickListener() {
        with(binding) {
            btnFeedbackTalkTranslate.setOnClickListener {
                btnFeedbackTalkTranslate.isSelected = !binding.btnFeedbackTalkTranslate.isSelected
                tvFeedbackTalkTranslate.isVisible = !binding.tvFeedbackTalkTranslate.isVisible
            }
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnFeedbackTalkClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 18.0f)
    }
}