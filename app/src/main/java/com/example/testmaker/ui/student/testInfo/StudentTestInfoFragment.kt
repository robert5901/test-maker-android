package com.example.testmaker.ui.student.testInfo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.FragmentStudentTestInfoBinding
import com.example.testmaker.models.student.StudentTest
import com.example.testmaker.ui.student.testInfo.viewModels.StudentTestInfoViewModel
import org.koin.android.ext.android.inject
import java.time.Duration

class StudentTestInfoFragment: Fragment(R.layout.fragment_student_test_info) {
    private val binding by viewBinding(FragmentStudentTestInfoBinding::bind)
    private val viewModel: StudentTestInfoViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test: StudentTest = arguments?.getParcelable(EXTRA_TEST) ?: return
        setData(test)

        configureViewModel()

        binding.start.setOnClickListener {
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.student_test_info_alert),
                actionTitle = resources.getString(R.string.student_test_info_alert_continue),
                action = { viewModel.startTest(test.id) }
            )
        }
    }

    private fun setData(test: StudentTest) {
        with(binding) {
            val timeString = Duration.parse(test.timeToSpend).toMinutes().toString()
            testName.text = test.name
            teacher.text = test.teacherName
            questionQuantity.text = test.questionsNumber.toString()
            attempts.text = test.availableAttempts.toString()
            time.text = resources.getString(R.string.student_test_info_time_for_test, timeString)
        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    companion object {
        private const val EXTRA_TEST = "extra_test"

        fun getNewInstance(test: StudentTest): StudentTestInfoFragment {
            return StudentTestInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_TEST, test)
                }
            }
        }
    }
}