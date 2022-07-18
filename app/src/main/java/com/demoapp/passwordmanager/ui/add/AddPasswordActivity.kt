package com.demoapp.passwordmanager.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.demoapp.passwordmanager.core.data.password.PasswordEntity
import com.demoapp.passwordmanager.core.di.PasswordVMFactory
import com.demoapp.passwordmanager.core.utils.DateConverter
import com.demoapp.passwordmanager.core.utils.INTENT_ID
import com.demoapp.passwordmanager.core.utils.INTENT_PROFILE
import com.demoapp.passwordmanager.core.utils.RandomPasswordGenerator
import com.demoapp.passwordmanager.databinding.ActivityAddPasswordBinding
import java.util.*

class AddPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPasswordBinding
    private lateinit var viewModel: AddPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = PasswordVMFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AddPasswordViewModel::class.java]

        binding.generatePasswordButton.setOnClickListener { generatePassword() }
        binding.addButton.setOnClickListener { addNewCredential() }

    }

    private fun generatePassword() {

        val includeUppercase = binding.uppercaseCheckBox.isChecked
        val includeNumbers = binding.numbersCheckbox.isChecked
        val includeSpecialCharacters = binding.symbolCheckbox.isChecked

        val randomPassword = RandomPasswordGenerator.generateNewRandomPassword(
            isUseUppercase = includeUppercase,
            isUseNumbers = includeNumbers,
            isUseSpecialChar = includeSpecialCharacters
        )

        binding.addEdPsw.setText(randomPassword)

    }

    private fun addNewCredential() {
        val appName = binding.addEdApp.text.toString().trim()
        val password = binding.addEdPsw.text.toString().trim()
        var email = binding.addEdEmail.text.toString().trim()
        val defaultEmail = intent?.getStringExtra(INTENT_PROFILE) as String
        val accId = intent?.getIntExtra(INTENT_ID, 0) as Int
        val currentDate = Calendar.getInstance()
        val dateString = DateConverter.convertDateIntoString(currentDate.time)

        if (appName.isEmpty() || password.isEmpty()) return
//        if (email.isEmpty()) email = defaultEmail else email
        // this is shorthand of code above
        email.ifEmpty { email = defaultEmail }

        val credential = PasswordEntity(
            appName = appName,
            emailAddress = email,
            password = password,
            date = dateString,
            accId = accId
        )

        viewModel.createNewPassword(credential)
        finish()

    }
}