package com.example.testmaker.ui.student.results

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentStudentResultsBinding
import com.example.testmaker.ui.student.results.adapters.StudentResultsAdapter
import com.example.testmaker.ui.student.results.viewModels.StudentResultsViewModel
import org.koin.android.ext.android.inject

class StudentResultsFragment: Fragment(R.layout.fragment_student_results) {
    private lateinit var adapter: StudentResultsAdapter

    private val binding by viewBinding(FragmentStudentResultsBinding::bind)
    private val viewModel: StudentResultsViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureAdapter()
        configureViewModel()

        viewModel.getResults()
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        observeOnStarted(viewModel.results) { results ->
            if (results != null) {
                adapter.set(results)
            }
        }
    }

    private fun configureAdapter() {
        adapter = StudentResultsAdapter()

        binding.recyclerView.adapter = adapter
    }
}