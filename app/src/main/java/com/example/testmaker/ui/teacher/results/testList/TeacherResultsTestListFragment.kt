package com.example.testmaker.ui.teacher.results.testList

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentTeacherResutlsTestListBinding
import com.example.testmaker.ui.teacher.results.testList.adapters.TeacherResultsTestListAdapter
import com.example.testmaker.ui.teacher.results.testList.viewModels.TeacherResultsTestListViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class TeacherResultsTestListFragment : Fragment(R.layout.fragment_teacher_resutls_test_list) {
    private lateinit var adapter: TeacherResultsTestListAdapter

    private val binding by viewBinding(FragmentTeacherResutlsTestListBinding::bind)
    private val viewModel: TeacherResultsTestListViewModel by inject()
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllTests()
        configureViewModel()
        configureAdapter()

        binding.search.addTextChangedListener { text ->
            filterTests(text.toString())
        }

        binding.clear.setOnClickListener {
            binding.search.text.clear()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.search.text.clear()
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.allTestsLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        observeOnStarted(viewModel.allTests) { tests ->
            if (tests == null) return@observeOnStarted
            adapter.differ.submitList(tests)
        }
    }

    private fun configureAdapter() {
        adapter = TeacherResultsTestListAdapter()

        adapter.onTestSelected = { test ->
            router.navigateTo(TeacherScreens.testResultsScreen(test.id, test.name))
        }

        binding.recyclerView.adapter = adapter
    }

    private fun filterTests(query: String) {
        val filteredTests = viewModel.allTests.value?.filter { test ->
            test.name.contains(query, true)
        }

        adapter.differ.submitList(filteredTests)
    }
}