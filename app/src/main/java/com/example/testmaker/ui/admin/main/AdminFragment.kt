package com.example.testmaker.ui.admin.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.AdminScreens
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.FragmentAdminBinding
import com.example.testmaker.ui.admin.main.adapters.AdminAdapter
import com.example.testmaker.ui.admin.main.viewModels.AdminViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class AdminFragment: Fragment(R.layout.fragment_admin) {
    private lateinit var adapter: AdminAdapter

    private val binding by viewBinding(FragmentAdminBinding::bind)
    private val viewModel: AdminViewModel by inject()
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTeacher.setOnClickListener {
            router.navigateTo(AdminScreens.addTeacher())
        }

        configureViewModel()
        configureAdapter()
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.teachers.value.isEmpty()) {
            viewModel.updateTeachers()
        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) {
            binding.progressBar.isVisible = it
        }

        observeOnStarted(viewModel.teachers) { teachers ->
            adapter.set(teachers)
        }
    }

    private fun configureAdapter() {
        adapter = AdminAdapter()

        adapter.onChangeClicked = { teacher ->
            router.navigateTo(AdminScreens.addTeacher(teacher))
        }
        adapter.onDeleteClicked = {
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.admin_delete_teacher_dialog_message),
                actionTitle = resources.getString(R.string.common_delete),
                action = { viewModel.deleteTeacher(it.id) }
            )
        }

        binding.recyclerView.adapter = adapter
    }
}