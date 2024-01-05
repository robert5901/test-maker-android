package com.example.testmaker.ui.student.testQuestion

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentStudentTestQuestionBinding
import com.example.testmaker.models.student.StudentTestStart

class StudentTestQuestionFragment: Fragment(R.layout.fragment_student_test_question){
    private val binding by viewBinding(FragmentStudentTestQuestionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test: StudentTestStart? = arguments?.getParcelable(EXTRA_TEST)

    }

    companion object {
        private const val EXTRA_TEST = "extra_test"

        fun getNewInstance(test: StudentTestStart): StudentTestQuestionFragment {
            return StudentTestQuestionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_TEST, test)
                }
            }
        }
    }
}