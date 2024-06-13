package com.talkable.presentation.mypage.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.talkable.R
import com.talkable.databinding.FragmentMyFeedbackDialogBinding

class MyFeedbackDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMyFeedbackDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyFeedbackDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSortTextViewClickListener()
        initCategoryTextViewClickListener()
        initSortLatestBtnViewClickListener()
        initSortAlphabeticalBtnViewClickListener()
        initCategoryWordBtnViewClickListener()
        initCategorySentenceBtnViewClickListener()
        initFinishBtnViewClickListener()
    }

    private fun initSortTextViewClickListener() {
        with(binding) {
            tvMyFeedbackSort.setOnClickListener {
                tvMyFeedbackSort.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.font
                    )
                )
                tvMyFeedbackCategory.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.font_gray
                    )
                )
                groupMyFeedbackSort.visibility = VISIBLE
                groupMyFeedbackCategory.visibility = GONE
            }
        }
    }

    private fun initCategoryTextViewClickListener() {
        with(binding) {
            tvMyFeedbackCategory.setOnClickListener {
                tvMyFeedbackCategory.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.font
                    )
                )
                tvMyFeedbackSort.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.font_gray
                    )
                )
                groupMyFeedbackSort.visibility = GONE
                groupMyFeedbackCategory.visibility = VISIBLE
            }
        }
    }

    private fun initSortLatestBtnViewClickListener() {
        with(binding) {
            btnMyFeedbackSortLatest.setOnClickListener {
                btnMyFeedbackSortLatest.isSelected = true
                btnMyFeedbackSortAlphabetical.isSelected = false
            }
        }
    }

    private fun initSortAlphabeticalBtnViewClickListener() {
        with(binding) {
            btnMyFeedbackSortAlphabetical.setOnClickListener {
                btnMyFeedbackSortAlphabetical.isSelected = true
                btnMyFeedbackSortLatest.isSelected = false
            }
        }
    }

    private fun initCategoryWordBtnViewClickListener() {
        with(binding) {
            btnMyFeedbackCategoryWord.setOnClickListener {
                btnMyFeedbackCategoryWord.isSelected = true
                btnMyFeedbackCategorySentence.isSelected = false
            }
        }
    }

    private fun initCategorySentenceBtnViewClickListener() {
        with(binding) {
            btnMyFeedbackCategorySentence.setOnClickListener {
                btnMyFeedbackCategorySentence.isSelected = true
                btnMyFeedbackCategoryWord.isSelected = false
            }
        }
    }

    private fun initFinishBtnViewClickListener() {
        binding.btnMyFeedbackFinish.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}