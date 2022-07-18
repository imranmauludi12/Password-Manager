package com.demoapp.passwordmanager.ui.home.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commonsware.cwac.saferoom.SQLCipherUtils
import com.demoapp.passwordmanager.core.di.PasswordVMFactory
import com.demoapp.passwordmanager.core.session.SessionManagement
import com.demoapp.passwordmanager.core.utils.FRAGMENT_EMAIL
import com.demoapp.passwordmanager.core.utils.FRAGMENT_ID
import com.demoapp.passwordmanager.core.utils.INTENT_ID
import com.demoapp.passwordmanager.core.utils.INTENT_PROFILE
import com.demoapp.passwordmanager.databinding.FragmentListBinding
import com.demoapp.passwordmanager.ui.add.AddPasswordActivity
import com.demoapp.passwordmanager.ui.detail.DetailPasswordActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ListViewModel
    private lateinit var listAdapter: ListPasswordAdapter
    @Inject lateinit var session: SessionManagement

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = PasswordVMFactory.getInstance(context)
        viewModel = ViewModelProvider(this, factory)[ListViewModel::class.java]

        val databaseState = SQLCipherUtils.getDatabaseState(context.applicationContext, "Password.db")
        if (databaseState.name.isNotEmpty()) {
            Log.d("cek db state", "value: ${databaseState.name}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                session.getSession().collect {
                    Log.d("cek session", "value: $it")
                }
            }
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvApp.layoutManager = layoutManager
        val argsID = arguments?.getInt(FRAGMENT_ID, 0) as Int
        val argsEmail = arguments?.getString(FRAGMENT_EMAIL) as String

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getAllPasswordStateFlow(argsID).collect { data ->
                    listAdapter = ListPasswordAdapter(data) {
                        val intent = Intent(requireContext(), DetailPasswordActivity::class.java)
                        intent.putExtra(INTENT_ID, it.id)
                        requireActivity().startActivity(intent)
                    }
                    binding.rvApp.adapter = listAdapter
                }
            }
        }

        binding.fab.setOnClickListener {
            val intent = Intent(requireContext(), AddPasswordActivity::class.java)
            intent.putExtra(INTENT_PROFILE, argsEmail)
            intent.putExtra(INTENT_ID, argsID)
            requireActivity().startActivity(intent)
        }

        initAction()
    }

    private fun initAction() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = viewHolder as ListPasswordAdapter.ViewHolder
                val credential = adapter.getCredential
                val position = adapter.getIndexPosition
                viewModel.deleteCredential(credential)
                listAdapter.notifyItemRemoved(position)

            }

        })
        itemTouchHelper.attachToRecyclerView(binding.rvApp)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}