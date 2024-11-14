package com.talkable.presentation.talk

import android.view.Gravity
import android.view.WindowManager
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.ToastTalkHintBinding

class TalkHintDialog() :
    BindingDialogFragment<ToastTalkHintBinding>(
        R.layout.toast_talk_hint
    ) {

    override fun onStart() {
        super.onStart()

        dialog?.window?.apply {
            setGravity(Gravity.BOTTOM)
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            attributes.y = 560

            addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun initView() {
        initCloseBtnClickListener()
    }

    private fun initCloseBtnClickListener() {
        binding.btnTalkClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 18.0f)
    }
}