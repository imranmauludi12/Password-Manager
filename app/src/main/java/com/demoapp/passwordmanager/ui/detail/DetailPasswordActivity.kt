package com.demoapp.passwordmanager.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.demoapp.passwordmanager.R
import com.demoapp.passwordmanager.core.di.PasswordVMFactory
import com.demoapp.passwordmanager.core.utils.INTENT_ID
import com.demoapp.passwordmanager.databinding.ActivityDetailPasswordBinding

class DetailPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPasswordBinding
    private lateinit var viewModel: DetailViewModel
    private var pswID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navbarDetail)

        val factory = PasswordVMFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

//        val passwordEntityId = intent?.getIntExtra(INTENT_ID, 0) as Int
        pswID = intent?.getIntExtra(INTENT_ID, 0) as Int

        viewModel.getDetailCredential(id = pswID).observe(this) {
            if (it != null) {
                binding.apply {
                    detailApp.setText(it.appName)
                    detailEmail.setText(it.emailAddress)
                    detailDate.setText(it.date)
                    detailPsw.setText(it.password)
                    detailDelete.setOnClickListener { _ ->
                        viewModel.deleteCredential(it)
                        finish()
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.update -> {
//                Toast.makeText(this, "confirm icon works", Toast.LENGTH_SHORT).show()
                updatePassword()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updatePassword() {
        binding.detailPsw.doAfterTextChanged { newInput ->
            if (newInput.toString().isNotEmpty()) {
                viewModel.changePasswordCredential(pswID, newInput.toString())
                Toast.makeText(this, "update success", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}