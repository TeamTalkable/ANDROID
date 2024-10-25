package com.talkable.presentation.login

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentAgreementBinding

class AgreementFragment : BindingFragment<FragmentAgreementBinding>(R.layout.fragment_agreement) {

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        navigateToBack()
        initStartBtnClickListener()
        initAgreementAllBtnClickListener()
        initSetTextColor()
    }

    // 뒤로가기
    private fun navigateToBack() {
        binding.btnAgreementBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    // 시작하기
    private fun initStartBtnClickListener() {
        binding.btnAgreementStart.setOnClickListener {
            findNavController().navigate(R.id.action_agreement_to_startSetting)
        }
    }

    // 전체 동의
    private fun initAgreementAllBtnClickListener() {
        binding.btnAgreementAll.setOnClickListener {
            with(binding) {
                val isChecked = btnAgreementAll.isChecked
                btnAgreementAge.isChecked = isChecked
                btnAgreementTerms.isChecked = isChecked
                btnAgreementPrivacy.isChecked = isChecked
                btnAgreementMarketing.isChecked = isChecked
            }
            checkAllConditions()
        }

        // 각각의 체크 박스 상태 변경 시 전체 동의 체크박스 상태 반영
        with(binding) {
            val checkboxes = listOf(
                btnAgreementAge,
                btnAgreementTerms,
                btnAgreementPrivacy,
                btnAgreementMarketing
            )

            // 나머지 체크박스의 상태 변경 리스너 설정
            checkboxes.forEach { checkBox ->
                checkBox.setOnCheckedChangeListener { _, _ ->
                    // 하나라도 체크 해제되면 btnAgreementAll도 체크 해제
                    btnAgreementAll.isChecked = checkboxes.all { it.isChecked }
                    checkAllConditions() // 조건에 맞춰 버튼 활성화
                }
            }
        }
    }

    // 모든 필수 체크 박스 선택 시 버튼 활성화
    private fun checkAllConditions() {
        with(binding) {
            btnAgreementStart.isEnabled = btnAgreementAge.isChecked &&
                    btnAgreementTerms.isChecked &&
                    btnAgreementPrivacy.isChecked
        }
    }

    private fun initSetTextColor() {
        with(binding) {
            tvAgreementAll.isSelected = btnAgreementAll.isSelected
            tvAgreementAge.isSelected = btnAgreementAge.isChecked
            tvAgreementTerms.isSelected = btnAgreementTerms.isChecked
            tvAgreementPrivacy.isSelected = btnAgreementPrivacy.isChecked
            tvAgreementMarketing.isSelected = btnAgreementMarketing.isChecked
        }
    }
}