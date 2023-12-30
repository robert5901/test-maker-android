package com.example.testmaker.ui.teacher.results.testResults

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentTeacherTestResultsBinding
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.teacher.StudentTestResult
import com.example.testmaker.ui.teacher.results.testResults.adapters.TeacherResultsTestResultsAdapter

class TeacherTestResultsFragment : Fragment(R.layout.fragment_teacher_test_results) {
    private lateinit var adapter: TeacherResultsTestResultsAdapter

    private val binding by viewBinding(FragmentTeacherTestResultsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testId: String? = arguments?.getString(EXTRA_TEST_ID)
        val testName: String? = arguments?.getString(EXTRA_TEST_NAME)

        binding.testName.text = testName

        configureAdapter()

        // TODO test data
        adapter.set(getResults())
    }

    private fun configureAdapter() {
        adapter = TeacherResultsTestResultsAdapter()

        binding.recyclerView.adapter = adapter
    }

    private fun getResults(): List<StudentTestResult> {
        return listOf(
            StudentTestResult("50/50", "Жмышенко Валерий Альбертович", Group("1", "4480")),
            StudentTestResult("40/50", "Игонин Юрий Андреевич", Group("2", "4481")),
            StudentTestResult("30/50", "Игонян Фанзиль Фунялович", Group("3", "4482")),
            StudentTestResult("35/50", "Максимова Варвара Глебовна", Group("4", "4483")),
            StudentTestResult("21/50", "Алешин Кирилл Владимирович", Group("1", "4480")),
            StudentTestResult("46/50", "Юдин Илья Максимович", Group("2", "4481")),
            StudentTestResult("39/50", "Тимофеева Алиса Егоровна", Group("3", "4482"))
        )
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