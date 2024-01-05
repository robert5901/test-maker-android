package com.example.testmaker.ui.student.testList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.StudentScreens
import com.example.testmaker.databinding.FragmentStudentTestListBinding
import com.example.testmaker.models.student.StudentTest
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

        adapter.set(
            // TODO test data
            listOf(
                StudentTest("1", 2, "Методы оптимизации", 2,
                    "Хазипова Альсина Айдаровна", "20"),
                StudentTest("2", 3, "Методы оптимизации 1", 2,
                    "Быкова Вероника Саввична", "25"),
                StudentTest("3", 1, "Методы оптимизации 2", 2,
                    "Гришин Максим Владимирович", "30"),
                StudentTest("4", 1, "Методы оптимизации 3", 2,
                    "Орлов Адам Михайлович", "15")
            )
        )
    }

    private fun configureViewModel() {

    }

    private fun configureAdapter() {
        adapter = StudentTestListAdapter()

        adapter.onSelected = { test ->
            router.navigateTo(StudentScreens.testInfo(test))
        }

        binding.recyclerView.adapter = adapter
    }
}