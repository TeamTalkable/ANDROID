package com.talkable.presentation.feedback

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentFeedbackLoadingBinding

class FeedbackLoadingFragment :
    BindingFragment<FragmentFeedbackLoadingBinding>(R.layout.fragment_feedback_loading) {
    override fun initView() {
        statusBarColorOf(R.color.white)
        setDelayToNavigate()
    }

    private fun setDelayToNavigate() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToExpressionFeedbackFragment()
        }, 1000)
    }

    private fun navigateToExpressionFeedbackFragment(){
        findNavController().navigate(R.id.action_feedback_loading_to_feedback_expression)
    }
}