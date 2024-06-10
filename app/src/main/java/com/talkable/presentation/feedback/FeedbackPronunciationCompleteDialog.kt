package com.talkable.presentation.feedback

import android.os.Handler
import android.os.Looper
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.FragmentFeedbackPronunciationCompleteDialogBinding

class FeedbackPronunciationCompleteDialog() :
    BindingDialogFragment<FragmentFeedbackPronunciationCompleteDialogBinding>(
        R.layout.fragment_feedback_pronunciation_complete_dialog
    ) {
    override fun initView() {
        setDismiss()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 40.0f)
    }

    private fun setDismiss() {
        Handler(Looper.getMainLooper()).postDelayed({
            dismiss()
        }, 1000)
    }
}