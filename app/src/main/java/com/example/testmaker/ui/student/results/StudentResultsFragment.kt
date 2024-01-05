package com.example.testmaker.ui.student.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentStudentResultsBinding
import com.example.testmaker.models.student.StudentResult
import com.example.testmaker.ui.student.results.adapters.StudentResultsAdapter

class StudentResultsFragment: Fragment(R.layout.fragment_student_results) {
    private lateinit var adapter: StudentResultsAdapter

    private val binding by viewBinding(FragmentStudentResultsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureAdapter()

        adapter.set(
            // TODO test data
            listOf(
                StudentResult("1", "23.01.2024 13:53", "Методы оптимизации",
                    "50/50", "Хазипова Альсина Айдаровна"),
                StudentResult("2", "23.01.2024 13:53", "Методы оптимизации 1",
                    "20/50", "Гришин Максим Владимирович"),
                StudentResult("3", "23.01.2024 13:53", "Методы оптимизации 2",
                    "30/50", "Быкова Вероника Саввична"),
                StudentResult("4", "23.01.2024 13:53", "Методы оптимизации 3",
                    "32/50", "Орлов Адам Михайлович")
            )
        )
    }

    private fun configureAdapter() {
        adapter = StudentResultsAdapter()

        binding.recyclerView.adapter = adapter
    }
}