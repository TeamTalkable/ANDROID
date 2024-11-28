package com.talkable.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.DialogKey
import com.talkable.databinding.FragmentSettingBinding

class MyPageSettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun initView() {
        initWithdrawBtnClickListener()
        initLogoutBtnClickListener()
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnAppBarBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initLogoutBtnClickListener() {
        binding.tvMyPageLogoutLabel.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() = LogoutDialog().show(
        childFragmentManager,
        DialogKey.LOGOUT_DIALOG
    )

    private fun initWithdrawBtnClickListener() {
        binding.tvMyPageWithdrawLabel.setOnClickListener {
            showWithdrawDialog()
        }
    }

    private fun showWithdrawDialog() = WithdrawDialog().show(
        childFragmentManager,
        DialogKey.WITHDRAW_DIALOG
    )
}