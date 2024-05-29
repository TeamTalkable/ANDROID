package com.talkable.presentation.login

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentAgreementBinding
import com.talkable.presentation.MainActivity

class AgreementFragment : BindingFragment<FragmentAgreementBinding>(R.layout.fragment_agreement) {

    override fun initView() {
        (activity as? MainActivity)?.hideBottomNavigation()

        initStartBtnClickListener()
        initAgreementAllBtnClickListener()
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
            with(binding){
                btnAgreementAge.isChecked = !btnAgreementAge.isChecked
                btnAgreementTerms.isChecked  = !btnAgreementTerms.isChecked
                btnAgreementPrivacy.isChecked = !btnAgreementPrivacy.isChecked
                btnAgreementMarketing.isChecked = !btnAgreementMarketing.isChecked
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showBottomNavigation()
    }
}