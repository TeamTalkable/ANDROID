package com.talkable.presentation

import android.view.View
import androidx.fragment.app.Fragment
import com.talkable.R
import com.talkable.core.base.BindingActivity
import com.talkable.databinding.ActivityMainBinding
import com.talkable.presentation.challenge.ChallengeFragment
import com.talkable.presentation.home.HomeFragment
import com.talkable.presentation.mypage.MyPageFragment

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        initHomeFragment()
    }

    private fun initHomeFragment(){
        val currentFragment = supportFragmentManager.findFragmentById(binding.fcvHome.id)
        if (currentFragment == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_home, HomeFragment())
                .commit()
        }
        clickBottomNavigation()
    }

    private fun clickBottomNavigation() {
        binding.bnvHome.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu_challenge -> {
                    replaceFragment(ChallengeFragment())
                    true
                }

                R.id.menu_mypage -> {
                    replaceFragment(MyPageFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_home, fragment)
            .commit()
    }

    fun hideBottomNavigation() {
        binding.bnvHome.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bnvHome.visibility = View.VISIBLE
    }
}