package com.talkable.presentation.login

import androidx.navigation.fragment.findNavController
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.talkable.R
import com.talkable.core.base.BindingFragment
import com.talkable.databinding.FragmentSignUpBinding
import com.talkable.presentation.MainActivity
import com.talkable.presentation.login.kakao.KakaoLoginCallback

class SignUpFragment : BindingFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val kakaoLoginCallback = KakaoLoginCallback { accessToken ->
        // 로그인 성공
        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
    }

    override fun initView() {
        (activity as? MainActivity)?.hideBottomNavigation()

        // 카카오 회원가입
        binding.btnLoginKakao.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                    if (error != null) {
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            // 사용자가 취소한 경우
                            return@loginWithKakaoTalk
                        } else {
                            // 다른 오류 발생 시 카카오 계정으로 로그인 시도
                            UserApiClient.instance.loginWithKakaoAccount(
                                requireContext(),
                                callback = kakaoLoginCallback::handleResult
                            )
                        }
                    } else if (token != null) {
                        // 로그인 성공
                        kakaoLoginCallback.handleResult(token, null)
                    }
                }
            } else {
                // 카카오 계정으로 로그인
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoLoginCallback::handleResult)
            }
        }

        //GropLayout으로 묶기
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