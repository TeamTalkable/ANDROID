package com.talkable.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingDialogFragment
import com.talkable.core.util.context.dialogFragmentResize
import com.talkable.databinding.DialogLogoutBinding

class LogoutDialog : BindingDialogFragment<DialogLogoutBinding>(R.layout.dialog_logout) {
    override fun initView() {
        initCancelBtnClickListener()
        initNavigateToLoginBtnClickListener()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 56.0f)
    }

    private fun initCancelBtnClickListener() {
        binding.ivLogoutCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initNavigateToLoginBtnClickListener() {
        binding.btnLogoutToLogin.setOnClickListener {
            dismiss()
            navigateToLoginFragment()
        }
    }

    private fun navigateToLoginFragment() =
        findNavController().navigate(R.id.action_my_page_to_login)
}