package com.talkable.presentation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.talkable.R
import com.talkable.core.base.BindingActivity
import com.talkable.databinding.ActivityMainBinding

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

        setBottomNaviVisible(navController)
    }

    private fun setBottomNaviVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvHome.visibility =
                if (destination.id in
                    listOf(
                        R.id.fragment_home,
                        R.id.fragment_challenge,
                        R.id.fragment_my_page
                    )
                ) {
                    View.VISIBLE
                } else {
                    View.GONE
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