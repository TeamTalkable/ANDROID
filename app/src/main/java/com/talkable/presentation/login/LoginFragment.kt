package com.talkable.presentation.login

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.core.util.fragment.statusBarColorOf
import com.talkable.databinding.FragmentLoginBinding
import com.talkable.presentation.MainActivity
import com.talkable.presentation.login.kakao.KakaoLoginService
import com.talkable.presentation.login.kakao.LoginViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : BindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private lateinit var loginViewModel: LoginViewModel

    override fun initView() {
        statusBarColorOf(R.color.main_3)
        (activity as? MainActivity)?.hideBottomNavigation()
        initLoginBtnClickListener()
        init()
        initKaKaoLoginObserve()
    }

    private fun init(){
        // KakaoLoginService 초기화
        val kakaoLoginService = KakaoLoginService(requireContext())

        // ViewModel 초기화
        val factory = LoginViewModelFactory(kakaoLoginService)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    // 로그인
    private fun initLoginBtnClickListener() {
        binding.btnLoginKakao.setOnClickListener {
            loginViewModel.kakaoLogin()
        }
    }

    // isKakaoLogin LiveData 관찰
    private fun initKaKaoLoginObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.isKakaoLogin.collect { isLoggedIn ->
                if (isLoggedIn) {
                    findNavController().navigate(R.id.action_loginFragment_to_agreementFragment)
                }
            }
        }
    }
}