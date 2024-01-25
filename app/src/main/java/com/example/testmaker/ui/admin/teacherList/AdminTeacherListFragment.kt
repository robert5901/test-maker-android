package com.example.testmaker.ui.admin.teacherList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.AdminScreens
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.FragmentAdminTeacherListBinding
import com.example.testmaker.ui.admin.teacherList.adapters.AdminTeacherListAdapter
import com.example.testmaker.ui.admin.teacherList.viewModels.AdminTeacherListViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class AdminTeacherListFragment: Fragment(R.layout.fragment_admin_teacher_list) {
    private lateinit var adapter: AdminTeacherListAdapter

    private val binding by viewBinding(FragmentAdminTeacherListBinding::bind)
    private val viewModel: AdminTeacherListViewModel by inject()
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()
        configureAdapter()

        binding.addTeacher.setOnClickListener {
            router.navigateTo(AdminScreens.addTeacherScreen())
        }

        binding.search.addTextChangedListener { text ->
            filterTeachers(text.toString())
        }

        binding.clear.setOnClickListener {
            binding.search.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.updateTeachers()
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) {
            binding.progressBar.isVisible = it
        }

        observeOnStarted(viewModel.teachers) { teachers ->
            adapter.differ.submitList(teachers)
        }
    }

    private fun configureAdapter() {
        adapter = AdminTeacherListAdapter()

        adapter.onChangeClicked = { teacher ->
            router.navigateTo(AdminScreens.addTeacherScreen(teacher))
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

    private fun filterTeachers(query: String) {
        val filteredTeachers = viewModel.teachers.value.filter { teacher ->
            teacher.name.contains(query, true)
        }

        adapter.differ.submitList(filteredTeachers)
    }
}