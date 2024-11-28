package com.talkable.presentation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.talkable.R
import com.talkable.core.base.BindingActivity
import com.talkable.core.util.context.statusBarColorOf
import com.talkable.data.SharedManager
import com.talkable.databinding.ActivityMainBinding

var firstTalk = true

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        initMainBottomNavigation()
    }

    private fun initMainBottomNavigation() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.fcv_home) as NavHostFragment).findNavController()
        binding.bnvHome.apply {
            setupWithNavController(navController)
            itemIconTintList = null
        }
        if (!SharedManager.checkLogin()) navController.navigate(R.id.onboardingFragment)
        setBottomNaviVisible(navController)
    }

    private fun setBottomNaviVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvHome.visibility =
                if (destination.id in
                    listOf(
                        R.id.fragment_home,
                        R.id.fragment_challenge,
                        R.id.fragment_review,
                        R.id.fragment_my_page
                    )
                ) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            when(destination.id){
                R.id.fragment_quiz_flash -> statusBarColorOf(R.color.white)
                R.id.fragment_talk -> statusBarColorOf(R.color.talk_toolbar_bg)
            }
        }
    }

    fun hideBottomNavigation() {
        binding.bnvHome.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bnvHome.visibility = View.VISIBLE
    }
}