package com.demoapp.passwordmanager.ui.auth.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.demoapp.passwordmanager.R
import com.demoapp.passwordmanager.core.data.user.UserEntity
import com.demoapp.passwordmanager.core.di.AuthenticationVMFactory
import com.demoapp.passwordmanager.core.session.SessionManagement
import com.demoapp.passwordmanager.core.utils.INTENT_ID
import com.demoapp.passwordmanager.core.utils.INTENT_PROFILE
import com.demoapp.passwordmanager.databinding.FragmentRegisterBinding
import com.demoapp.passwordmanager.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel
    private var isSuccessfullyAdd: Boolean = false

    @Inject lateinit var session: SessionManagement
    private lateinit var masterKey: String
    private lateinit var sharedPreference: SharedPreferences

    companion object {
        private const val FILE_NAME = "some_secret_prefs"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = AuthenticationVMFactory.getInstance(context)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        }
        sharedPreference = EncryptedSharedPreferences.create(
            FILE_NAME,
            masterKey,
            context.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerEdKey.doOnTextChanged { text, _, _, _ ->
            val email = binding.registerEdEmail.text.toString()
            val name = binding.registerEdName.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                binding.registerButton.isEnabled = text != null && text.length == 6
            }
        }

        binding.registerButton.setOnClickListener {
            registerNewAccount()
        }
    }

    private fun registerNewAccount() {

        val name = binding.registerEdName.text.toString()
        val email = binding.registerEdEmail.text.toString()
        val key = binding.registerEdKey.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty()) {

            val newUser = UserEntity(
                accountName = name,
                accountEmail = email,
                accountKey = key
            )

            viewModel.register(newUser).observe(viewLifecycleOwner) { isComplete ->
                isSuccessfullyAdd = isComplete
            }

            if (isSuccessfullyAdd) {
                viewModel.checkAccount(newUser.accountKey).observe(viewLifecycleOwner) {
                    if (it != null) {
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        intent.putExtra(INTENT_ID, it.Id)
                        intent.putExtra(INTENT_PROFILE, it.accountEmail)
                        requireActivity().startActivity(intent)

                        viewLifecycleOwner.lifecycleScope
                            .launch { session.editSession(it.Id) }

                    }
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.error_same_password), Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show()
        }
    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}