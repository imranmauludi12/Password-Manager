package com.demoapp.passwordmanager.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.demoapp.passwordmanager.R
import com.demoapp.passwordmanager.core.session.SessionManagement
import com.demoapp.passwordmanager.core.utils.INTENT_ID
import com.demoapp.passwordmanager.core.utils.INTENT_PROFILE
import com.demoapp.passwordmanager.databinding.ActivityHomeBinding
import com.demoapp.passwordmanager.ui.auth.AuthenticationActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    @Inject lateinit var session: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navbar)

        val userID = intent?.getIntExtra(INTENT_ID, 0) as Int
        val userEmail = intent?.getStringExtra(INTENT_PROFILE) as String

        val tabs = binding.tabs
        val viewPager = binding.viewPager
        val pagerAdapter = PagerAdapter(this, userId = userID, userEmail = userEmail)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, pos ->
            tab.text = resources.getString(TAB_TITLES[pos]).replaceFirstChar { it.uppercase() }
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this, "setting page", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.logout -> {
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                lifecycleScope.launch {
                    session.editSession(-1)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.home_title,
            R.string.profile_title
        )
    }
}