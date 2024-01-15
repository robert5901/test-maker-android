package com.example.testmaker.ui.admin.groups

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.FragmentAdminGroupListBinding
import com.example.testmaker.ui.admin.groups.adapters.AdminGroupListAdapter
import com.example.testmaker.ui.admin.groups.adapters.AdminGroupsViewModel
import org.koin.android.ext.android.inject

class AdminGroupListFragment: Fragment(R.layout.fragment_admin_group_list) {
    private lateinit var adapter: AdminGroupListAdapter

    private val binding by viewBinding(FragmentAdminGroupListBinding::bind)
    private val viewModel: AdminGroupsViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()
        configureAdapter()

        binding.search.addTextChangedListener { text ->
            filterGroups(text.toString())
        }

        binding.clear.setOnClickListener {
            binding.search.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.updateGroups()
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isVisible ->
            binding.progressBar.isVisible = isVisible
        }

        observeOnStarted(viewModel.groups) { group ->
            adapter.differ.submitList(group)
        }
    }

    private fun configureAdapter() {
        adapter = AdminGroupListAdapter()

        adapter.onDeleteClicked = {
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.admin_delete_teacher_dialog_message),
                actionTitle = resources.getString(R.string.common_delete),
                // TODO удалять группу
//                action = { viewModel.deleteGroup(it.id) }
            )
        }

        binding.recyclerView.adapter = adapter
    }

    private fun filterGroups(query: String) {
        val filteredGroups = viewModel.groups.value.filter { group ->
            group.title.contains(query, true)
        }

        adapter.differ.submitList(filteredGroups)
    }
}