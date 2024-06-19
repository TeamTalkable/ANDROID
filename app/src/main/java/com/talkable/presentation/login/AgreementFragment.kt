package com.talkable.presentation.login

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentAgreementBinding

class AgreementFragment : BindingFragment<FragmentAgreementBinding>(R.layout.fragment_agreement) {

    override fun initView() {
        navigateToBack()
        initStartBtnClickListener()
        initAgreementAllBtnClickListener()
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
            findNavController().navigate(R.id.action_agreementFragment_to_homeFragment)
        }
    }

    // 전체 동의
    private fun initAgreementAllBtnClickListener() {
        binding.btnAgreementAll.setOnClickListener {
            with(binding) {
                btnAgreementAge.isChecked = !btnAgreementAge.isChecked
                btnAgreementTerms.isChecked = !btnAgreementTerms.isChecked
                btnAgreementPrivacy.isChecked = !btnAgreementPrivacy.isChecked
                btnAgreementMarketing.isChecked = !btnAgreementMarketing.isChecked
            }
            //조건 검사 다시 해야함 일단 임시로 해둠
            binding.btnAgreementStart.isEnabled = true
        }
    }
}