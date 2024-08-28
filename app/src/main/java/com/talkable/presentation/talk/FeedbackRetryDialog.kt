package com.talkable.presentation.talk

import android.os.Handler
import android.os.Looper
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.FragmentFeedbackRetryDialogBinding

class FeedbackRetryDialog() :
    BindingDialogFragment<FragmentFeedbackRetryDialogBinding>(
        R.layout.fragment_feedback_retry_dialog
    ) {
    override fun initView() {
        setDismiss()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 41.0f)
    }

    private fun setDismiss() {
        Handler(Looper.getMainLooper()).postDelayed({
            dismiss()
        }, 3000)
    }
}