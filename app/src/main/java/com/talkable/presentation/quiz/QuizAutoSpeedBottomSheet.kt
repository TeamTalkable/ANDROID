package com.talkable.presentation.quiz

import android.view.View
import android.widget.CheckBox
import com.talkable.R
import com.talkable.core.base.BindingBottomSheetFragment
import com.talkable.databinding.BottomSheetQuizAutoSpeedBinding

class QuizAutoSpeedBottomSheet :
    BindingBottomSheetFragment<BottomSheetQuizAutoSpeedBinding>(R.layout.bottom_sheet_quiz_auto_speed) {

    private var lastCheckedCheckBox: CheckBox? = null

    override fun initView() {
        lastCheckedCheckBox = binding.cbQuizAutoSpeedSkipNow
        initCompleteBtnClickListener()
        setCheckBox()
    }

    private fun initCompleteBtnClickListener() {
        binding.btnQuizAutoSpeed.setOnClickListener {
            dismiss()
        }
    }

    private fun setCheckBox() {
        val checkBoxListener = View.OnClickListener { view ->
            val checkBox = view as CheckBox

            if (checkBox.isChecked) {
                lastCheckedCheckBox?.isChecked = false
                lastCheckedCheckBox = checkBox
            }
            checkBox.isChecked = true
        }

        binding.cbQuizAutoSpeedSkipNow.setOnClickListener(checkBoxListener)
        binding.cbQuizAutoSpeedSkipSecond1.setOnClickListener(checkBoxListener)
        binding.cbQuizAutoSpeedSkipSecond2.setOnClickListener(checkBoxListener)
    }

}