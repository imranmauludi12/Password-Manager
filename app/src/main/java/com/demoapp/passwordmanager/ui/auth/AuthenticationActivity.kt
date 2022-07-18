package com.demoapp.passwordmanager.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demoapp.passwordmanager.R
import com.demoapp.passwordmanager.databinding.ActivityAuthenticationBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val viewPager = binding.viewPagerRegister
        val tabs = binding.tabsRegister
        val adapter = SectionPagerAdapter(this)
        viewPager.adapter = adapter
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position]).lowercase()
        }.attach()

    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.login_title,
            R.string.register_title
        )
    }
}