package com.example.testmaker.ui.teacher.configureTest

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.ChooseGroupsDialogFragmentBinding
import com.example.testmaker.databinding.FragmentTeacherConfigureTestBinding
import com.example.testmaker.di.modules.groupsModule
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.test.ConfigureTestBody
import com.example.testmaker.models.test.Test
import com.example.testmaker.ui.teacher.configureTest.viewModels.TeacherTestConfigureViewModel
import org.koin.android.ext.android.inject

class TeacherConfigureTestFragment : Fragment(R.layout.fragment_teacher_configure_test) {
    private val binding by viewBinding(FragmentTeacherConfigureTestBinding::bind)
    private val viewModel: TeacherTestConfigureViewModel by inject()
    private val margin by lazy { resources.getDimensionPixelSize(R.dimen.student_test_question_radio_button_margin_bottom) }
    private val textSize by lazy { resources.getDimension(R.dimen.student_test_question_radio_button_text_size) }

    private val checkBoxList: MutableList<CheckBox> = mutableListOf()
    private var selectedGroups: MutableList<Group> = mutableListOf()
    private var test: Test? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        test = arguments?.getParcelable(EXTRA_TEST)

        configureViewModel()
        setData(test)

        viewModel.getGroups()

        binding.save.setOnClickListener {
            val testId = test?.id ?: return@setOnClickListener

            // TODO endTime и startTime выставлять через календарь 2 времени и в TextView записывать через дефис
            val endTime = ""
            val startTime = ""
            val timeToSpend = ""
            val availableAttempts = binding.attempts.text.toString().toInt()

            val configTestBody = ConfigureTestBody(
                endTime = endTime,
                startTime = startTime,
                groups = selectedGroups,
                timeToSpend = timeToSpend,

                availableAttempts = availableAttempts,
                randomAnswers = binding.randomAnswersSwitch.isChecked,
                randomQuestions = binding.randomQuestionsSwitch.isChecked
            )

            viewModel.saveConfig(testId, configTestBody)
        }

        binding.chooseGroups.setOnClickListener {
            val groups = viewModel.groups.value ?: return@setOnClickListener
            openChooseGroupsDialog(groups)
        }

        binding.accessTime.setOnClickListener {

        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isLoading ->
            binding.progressBar.isVisible = isLoading
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

    private fun openChooseGroupsDialog(groups: List<Group>) {
        val dialogBinding = ChooseGroupsDialogFragmentBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.checkBoxLayout.removeAllViews()
        checkBoxList.clear()

        groups.forEach { group ->
            val checkBox = CheckBox(requireContext())
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 0, 0, margin)
            checkBox.layoutParams = params
            checkBox.text = group.title
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            dialogBinding.checkBoxLayout.addView(checkBox)
            checkBoxList.add(checkBox)
        }

        checkBoxList.forEach { checkBox ->
            val group = groups.find { it.title == checkBox.text }
            checkBox.isChecked = group != null && selectedGroups.contains(group)
        }

        dialogBinding.choose.setOnClickListener {
            selectedGroups.clear()
            val groupList = checkBoxList.filter { it.isChecked }.mapNotNull { checkBox ->
                groups.find { it.title == checkBox.text }
            }
            selectedGroups.addAll(
                groupList
            )

            dialog.cancel()
        }

        dialog.show()
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