package com.example.testmaker.ui.teacher.results.testResults

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentTeacherTestResultsBinding
import com.example.testmaker.models.teacher.StudentTestResult
import com.example.testmaker.ui.teacher.results.testResults.adapters.TeacherResultsTestResultsAdapter
import com.example.testmaker.ui.teacher.results.testResults.viewModels.TeacherTestResultsViewModel
import org.koin.android.ext.android.inject

class TeacherTestResultsFragment : Fragment(R.layout.fragment_teacher_test_results) {
    private lateinit var adapter: TeacherResultsTestResultsAdapter

    private val binding by viewBinding(FragmentTeacherTestResultsBinding::bind)
    private val viewModel: TeacherTestResultsViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testId: String? = arguments?.getString(EXTRA_TEST_ID)
        val testName: String? = arguments?.getString(EXTRA_TEST_NAME)

        if (testId != null) {
            viewModel.getResults(testId)
        }

        binding.testName.text = testName

        configureViewModel()
        configureAdapter()

        binding.search.addTextChangedListener { text ->
            filterResults(text.toString())
        }

        binding.clear.setOnClickListener {
            binding.search.text.clear()
        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.resultsLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        observeOnStarted(viewModel.results) { results ->
            if (results == null) return@observeOnStarted
            adapter.differ.submitList(results)
        }
    }

    private fun configureAdapter() {
        adapter = TeacherResultsTestResultsAdapter()

        binding.recyclerView.adapter = adapter
    }

    private fun filterResults(query: String) {
        val queryIsDigits: Boolean = query.matches(Regex("\\d*"))

        val results = viewModel.results.value ?: return
        val filteredResults: List<StudentTestResult> =
            if (queryIsDigits) {
                results.filter { result ->
                    result.studentGroup.title.contains(query, true)
                }
            } else {
                results.filter { result ->
                    result.studentName.contains(query, true)
                }
            }

        adapter.differ.submitList(filteredResults)
    }

    companion object {
        private const val EXTRA_TEST_ID = "extra_test_id"
        private const val EXTRA_TEST_NAME = "extra_test_name"

        fun getNewInstance(testId: String, testName: String): TeacherTestResultsFragment {

            return TeacherTestResultsFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TEST_ID, testId)
                    putString(EXTRA_TEST_NAME, testName)
                }
            }
        }
    }
}