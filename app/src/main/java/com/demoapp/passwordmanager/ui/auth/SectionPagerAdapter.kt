package com.demoapp.passwordmanager.ui.auth

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demoapp.passwordmanager.ui.auth.login.LoginFragment
import com.demoapp.passwordmanager.ui.auth.register.RegisterFragment

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = TAB_AMOUNT

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = LoginFragment()
            1 -> fragment = RegisterFragment()
        }
        return fragment as Fragment
    }

    companion object {
        private const val TAB_AMOUNT = 2
    }
}