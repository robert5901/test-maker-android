package com.example.testmaker.ui.student.testPassResult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.StudentScreens
import com.example.testmaker.databinding.FragmentTestPassResultBinding
import com.example.testmaker.models.student.StudentResult
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class StudentTestPassResultFragment : Fragment(R.layout.fragment_test_pass_result) {
    private val binding by viewBinding(FragmentTestPassResultBinding::bind)
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result: StudentResult = arguments?.getParcelable(EXTRA_RESULT) ?: return

        binding.result.text = result.result

        binding.mainMenu.setOnClickListener {
            router.backTo(StudentScreens.studentScreen())
        }
    }

    companion object {
        private const val EXTRA_RESULT = "extra_result"

        fun getNewInstance(result: StudentResult): StudentTestPassResultFragment {
            return StudentTestPassResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_RESULT, result)
                }
            }
        }
    }
}