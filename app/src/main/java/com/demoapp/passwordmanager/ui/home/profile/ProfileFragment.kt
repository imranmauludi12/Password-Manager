package com.demoapp.passwordmanager.ui.home.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.demoapp.passwordmanager.core.di.AuthenticationVMFactory
import com.demoapp.passwordmanager.core.utils.FRAGMENT_ID
import com.demoapp.passwordmanager.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = AuthenticationVMFactory.getInstance(context)
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.getInt(FRAGMENT_ID, 0) as Int
        viewModel.setUserId(args)

        viewModel.profile.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.valueName.text = it.accountName
                binding.valueEmail.text = it.accountEmail
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}