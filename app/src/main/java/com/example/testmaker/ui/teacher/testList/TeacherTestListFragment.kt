package com.example.testmaker.ui.teacher.testList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.FragmentTeacherTestListBinding
import com.example.testmaker.ui.TestListData
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

        binding.createTest.setOnClickListener {
//            viewModel.createTest()
            // TODO test data
            router.navigateTo(TeacherScreens.testQuestionListScreen("1", null))
        }

        adapter.set(
            // TODO test data
            TestListData.tests
        )
    }

    private fun configureViewModel() {
//        observeOnStarted(viewModel.testId) {
//            router.navigateTo(TeacherScreens.createTest(testId))
//        }

        observeOnStarted(viewModel.teacherTest) { test ->
            if (test == null) return@observeOnStarted
            router.navigateTo(TeacherScreens.testQuestionListScreen(test.id, test))
        }

        observeOnStarted(viewModel.teacherTestLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun configureAdapter() {
        adapter = TeacherTestListAdapter()

        adapter.onChangeClicked = { test ->
            viewModel.getTeacherTest(test.id)
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
            router.navigateTo(TeacherScreens.configureTestScreen(test))
        }

        binding.recyclerView.adapter = adapter
    }
}