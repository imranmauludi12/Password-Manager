package com.demoapp.passwordmanager.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.demoapp.passwordmanager.core.di.AuthenticationVMFactory
import com.demoapp.passwordmanager.core.session.SessionManagement
import com.demoapp.passwordmanager.core.utils.INTENT_ID
import com.demoapp.passwordmanager.core.utils.INTENT_PROFILE
import com.demoapp.passwordmanager.databinding.FragmentLoginBinding
import com.demoapp.passwordmanager.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel
    @Inject lateinit var session: SessionManagement

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = AuthenticationVMFactory.getInstance(context)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edKey = binding.edAccountKey
        edKey.doOnTextChanged { text, _, _, _ ->
            binding.loginButton.isEnabled = text != null && text.length >= 6
        }

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val key = binding.edAccountKey.text.toString().trim()

        if (key.isNotEmpty()) {
            viewModel.login(key).observe(viewLifecycleOwner) {
                if (it != null) {

                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    intent.putExtra(INTENT_ID, it.Id)
                    intent.putExtra(INTENT_PROFILE, it.accountEmail)
                    requireActivity().startActivity(intent)

                    viewLifecycleOwner.lifecycleScope.launch {
                        session.editSession(it.Id)
                    }

                } else {
                    Toast.makeText(requireContext(), "account don't found!", Toast.LENGTH_SHORT).show()
                }
            }
            binding.edAccountKey.setText("")
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}