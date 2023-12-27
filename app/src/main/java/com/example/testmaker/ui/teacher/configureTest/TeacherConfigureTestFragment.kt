package com.example.testmaker.ui.teacher.configureTest

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentTeacherConfigureTestBinding
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.test.Test

class TeacherConfigureTestFragment : Fragment(R.layout.fragment_teacher_configure_test) {
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private val binding by viewBinding(FragmentTeacherConfigureTestBinding::bind)

    private var selectedGroupTitle: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val test: Test? = arguments?.getParcelable(EXTRA_TEST)

        configureSpinner()
        setData(test)

        // TODO test data. get from request. like in registration
        val groups = listOf(
            Group("1", "4480"),
            Group("2", "4481"),
            Group("3", "4482"),
            Group("4", "4483"),
            Group("5", "4484")
        )
        spinnerAdapter.clear()
        spinnerAdapter.addAll(groups.map { it.title })
        spinnerAdapter.notifyDataSetChanged()
    }

    private fun configureSpinner() {
        spinnerAdapter = ArrayAdapter(binding.groupSpinner.context, android.R.layout.simple_list_item_1)
        binding.groupSpinner.adapter = spinnerAdapter

        binding.groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = binding.groupSpinner.selectedItem
                if (selectedItem is String) {
                    selectedGroupTitle = selectedItem
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    private fun setData(test: Test?) {
        if (test == null) return

        with(binding) {
            // TODO test data
            accessTime.text = "13:30-14:00 15.11.23"
            timeForTest.setText("20")
            randomQuestionsSwitch.isChecked = test.randomQuestions
            randomAnswersSwitch.isChecked = test.randomAnswers

            testName.text = test.name
            attempts.setText(test.availableAttempts.toString())
        }
    }

    companion object {
        private const val EXTRA_TEST = "extra_test"

        fun getNewInstance(test: Test?): TeacherConfigureTestFragment {
            if (test == null) return TeacherConfigureTestFragment()

            return TeacherConfigureTestFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_TEST, test)
                }
            }
        }
    }
}