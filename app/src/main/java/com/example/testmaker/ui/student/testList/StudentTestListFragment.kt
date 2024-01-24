package com.example.testmaker.ui.student.testList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.StudentScreens
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentStudentTestListBinding
import com.example.testmaker.ui.student.testList.adapters.StudentTestListAdapter
import com.example.testmaker.ui.student.testList.viewModels.StudentTestListViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class StudentTestListFragment: Fragment(R.layout.fragment_student_test_list) {
    private lateinit var adapter: StudentTestListAdapter

    private val binding by viewBinding(FragmentStudentTestListBinding::bind)
    private val viewModel: StudentTestListViewModel by inject()
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()
        configureAdapter()

        viewModel.getTests()

        binding.search.addTextChangedListener { text ->
            filterTests(text.toString())
        }

        binding.clear.setOnClickListener {
            binding.search.text.clear()
        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        observeOnStarted(viewModel.tests) { tests ->
            adapter.differ.submitList(tests)
        }
    }

    private fun configureAdapter() {
        adapter = StudentTestListAdapter()

        adapter.onSelected = { test ->
            router.navigateTo(StudentScreens.testInfo(test))
        }

        binding.recyclerView.adapter = adapter
    }

    private fun filterTests(query: String) {
        val filteredTests = viewModel.tests.value?.filter { test ->
            test.name.contains(query, true)
        }

        adapter.differ.submitList(filteredTests)
    }
}