package com.example.testmaker.ui.teacher.configureTest

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.Action
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.ChooseGroupsDialogFragmentBinding
import com.example.testmaker.databinding.FragmentTeacherConfigureTestBinding
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.test.ConfigureTestBody
import com.example.testmaker.models.test.Test
import com.example.testmaker.ui.teacher.configureTest.viewModels.TeacherTestConfigureViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class TeacherConfigureTestFragment : Fragment(R.layout.fragment_teacher_configure_test) {
    private val binding by viewBinding(FragmentTeacherConfigureTestBinding::bind)
    private val errorManager: ErrorManager by inject()
    private val viewModel: TeacherTestConfigureViewModel by inject()
    private val router: Router by inject()
    private val margin by lazy { resources.getDimensionPixelSize(R.dimen.student_test_question_radio_button_margin_bottom) }
    private val textSize by lazy { resources.getDimension(R.dimen.student_test_question_radio_button_text_size) }

    private val checkBoxList: MutableList<CheckBox> = mutableListOf()
    private var selectedGroups: MutableList<Group> = mutableListOf()
    private val calendar = Calendar.getInstance()
    private var test: Test? = null
    private var startTime: String? = null
    private var endTime: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        test = arguments?.getParcelable(EXTRA_TEST)

        configureViewModel()
        setData(test)

        viewModel.getGroups()

        configureClickListeners()
    }

    private fun configureClickListeners() {
        binding.save.setOnClickListener {
            val testId = test?.id ?: return@setOnClickListener

            val isValid = validateData()
            if (!isValid) return@setOnClickListener

            val endTime = endTime ?: return@setOnClickListener
            val startTime = startTime ?: return@setOnClickListener

            val timeToSpend = Duration.ofMinutes(binding.timeForTest.text.toString().toLong()).toString()
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

        binding.accessTimeStart.setOnClickListener {
            showDateTimePicker(binding.accessTimeStart) {
                startTime = it
            }
        }

        binding.accessTimeEnd.setOnClickListener {
            showDateTimePicker(binding.accessTimeEnd) {
                endTime = it
            }
        }
    }

    private fun validateData(): Boolean {
        if (selectedGroups.isEmpty()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_configure_test_empty_groups_error))
            return false
        }

        if (binding.accessTimeStart.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_configure_test_blank_start_time_error))
            return false
        }

        if (binding.accessTimeEnd.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_configure_test_blank_end_time_error))
            return false
        }

        if (binding.timeForTest.text.toString().isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_configure_test_blank_time_for_test_error))
            return false
        }

        if (binding.attempts.text.toString().isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_configure_test_blank_attempts_error))
            return false
        }

        return true
    }

    private fun showDateTimePicker(textView: TextView, onDateTimeSelected: Action<String>) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                showTimePicker(textView, onDateTimeSelected)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun showTimePicker(textView: TextView, onDateTimeSelected: Action<String>) {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                val sdf = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())
                val selectedTime = sdf.format(calendar.time)
                textView.text = selectedTime
                onDateTimeSelected(calendar.toInstant().toString())
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )

        timePickerDialog.show()
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
//            timeForTest.setText("20")


            if (test.startTime == "2024-01-30T14:04:31.475246066Z") {
                accessTimeStart.text = resources.getString(R.string.teacher_configure_test_access_time_start)
                accessTimeEnd.text = resources.getString(R.string.teacher_configure_test_access_time_end)
            } else {
                val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy", Locale.getDefault())
                val startTimeInstant = Instant.parse(test.startTime)
                val startTimeLocalDateTime = LocalDateTime.ofInstant(startTimeInstant, ZoneId.systemDefault())
                val endTimeInstant = Instant.parse(test.endTime)
                val endTimeLocalDateTime = LocalDateTime.ofInstant(endTimeInstant, ZoneId.systemDefault())

                startTime = test.startTime
                endTime = test.endTime

                accessTimeStart.text = startTimeLocalDateTime.format(formatter)
                accessTimeEnd.text = endTimeLocalDateTime.format(formatter)
            }

            timeForTest.setText(Duration.parse(test.timeToSpend).toMinutes().toString())
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

            checkBox.isChecked = test?.groups?.contains(group) == true

            dialogBinding.checkBoxLayout.addView(checkBox)
            checkBoxList.add(checkBox)
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