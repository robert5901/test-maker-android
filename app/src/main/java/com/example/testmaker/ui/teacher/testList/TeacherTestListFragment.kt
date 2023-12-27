package com.example.testmaker.ui.teacher.testList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.FragmentTeacherTestListBinding
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.test.Test
import com.example.testmaker.ui.teacher.testList.adapters.TeacherTestListAdapter
import com.example.testmaker.ui.teacher.testList.viewModels.TeacherTestListViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class TeacherTestListFragment : Fragment(R.layout.fragment_teacher_test_list) {
    private lateinit var adapter: TeacherTestListAdapter

    private val binding by viewBinding(FragmentTeacherTestListBinding::bind)
    private val viewModel: TeacherTestListViewModel by inject()
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()
        configureAdapter()

        adapter.set(
            getTestData()
        )
    }

    private fun configureViewModel() {

    }

    private fun configureAdapter() {
        adapter = TeacherTestListAdapter()

        adapter.onChangeClicked = { test ->
//            router.navigateTo(TeacherScreens.addTest(test))
        }
        adapter.onDeleteClicked = {
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.teacher_test_list_delete_dialog_message),
                actionTitle = resources.getString(R.string.common_delete),
//                action = { viewModel.deleteTest(it.id) }
            )
        }
        adapter.onSelected = { test ->
            router.navigateTo(TeacherScreens.configureTest(test))
        }

        binding.recyclerView.adapter = adapter
    }

    private fun getTestData(): List<Test> {
        return listOf(
            Test("1", 2, "2023-12-25T17:10:04.865Z",
                listOf(
                    Group("1", "4480"),
                    Group("2", "4481"),
                    Group("3", "4482"),
                    Group("4", "4483")
                ),
                "Методы оптимизации", true, false,
                "2023-12-25T17:10:04.865Z", "PT20M"
            ),
            Test("2", 2, "2023-12-25T17:10:04.865Z",
                listOf(
                    Group("1", "4480"),
                    Group("2", "4481"),
                    Group("3", "4482"),
                    Group("4", "4483")
                ),
                "Методы оптимизации 1", true, true,
                "2023-12-25T17:10:04.865Z", "PT20M"
            ),
            Test("3", 2, "2023-12-25T17:10:04.865Z",
                listOf(
                    Group("1", "4480"),
                    Group("2", "4481"),
                    Group("3", "4482"),
                    Group("4", "4483")
                ),
                "Методы оптимизации 2", true, true,
                "2023-12-25T17:10:04.865Z", "PT20M"
            ),
            Test("4", 2, "2023-12-25T17:10:04.865Z",
                listOf(
                    Group("1", "4480"),
                    Group("2", "4481"),
                    Group("3", "4482"),
                    Group("4", "4483")
                ),
                "Методы оптимизации 3", true, true,
                "2023-12-25T17:10:04.865Z", "PT20M"
            ),
            Test("5", 2, "2023-12-25T17:10:04.865Z",
                listOf(
                    Group("1", "4480"),
                    Group("2", "4481"),
                    Group("3", "4482"),
                    Group("4", "4483")
                ),
                "Методы оптимизации 4", true, true,
                "2023-12-25T17:10:04.865Z", "PT20M"
            ),
            Test("6", 2, "2023-12-25T17:10:04.865Z",
                listOf(
                    Group("1", "4480"),
                    Group("2", "4481"),
                    Group("3", "4482"),
                    Group("4", "4483")
                ),
                "Методы оптимизации 5", true, true,
                "2023-12-25T17:10:04.865Z", "PT20M"
            ),

        )
    }
}