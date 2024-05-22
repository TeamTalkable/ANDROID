package com.talkable.presentation.login

import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentLoginBinding
import com.talkable.presentation.MainActivity

class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    override fun initView() {

        (activity as? MainActivity)?.hideBottomNavigation()

        // 회원가입으로 이동
        binding.tvLoginSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.showBottomNavigation()
    }
}