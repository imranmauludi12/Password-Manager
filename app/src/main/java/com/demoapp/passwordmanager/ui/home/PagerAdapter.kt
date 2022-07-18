package com.demoapp.passwordmanager.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demoapp.passwordmanager.core.utils.FRAGMENT_EMAIL
import com.demoapp.passwordmanager.core.utils.FRAGMENT_ID
import com.demoapp.passwordmanager.ui.home.list.ListFragment
import com.demoapp.passwordmanager.ui.home.profile.ProfileFragment

class PagerAdapter(
    activity: AppCompatActivity,
    private val userId: Int = 0,
    private val userEmail: String = ""
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return TAB_TOTAL
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putInt(FRAGMENT_ID, userId)
        bundle.putString(FRAGMENT_EMAIL, userEmail)
        when (position) {
            0 -> fragment = ListFragment()
            1 -> fragment = ProfileFragment()
        }
        fragment?.arguments = bundle
        return fragment as Fragment
    }

    companion object {
        private const val TAB_TOTAL = 2
    }
}