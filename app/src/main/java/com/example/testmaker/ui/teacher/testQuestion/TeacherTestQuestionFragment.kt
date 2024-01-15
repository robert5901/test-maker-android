package com.example.testmaker.ui.teacher.testQuestion

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentTeacherAddQuestionBinding
import com.example.testmaker.models.teacher.TeacherTestQuestion
import com.example.testmaker.models.teacher.TeacherTestQuestionBody
import com.example.testmaker.ui.teacher.testQuestion.viewModels.TeacherTestQuestionViewModel
import com.example.testmaker.ui.teacher.testQuestion.views.TeacherTestQuestionVariantView
import org.koin.android.ext.android.inject

class TeacherTestQuestionFragment: Fragment(R.layout.fragment_teacher_add_question) {
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private val binding by viewBinding(FragmentTeacherAddQuestionBinding::bind)
    private val viewModel: TeacherTestQuestionViewModel by inject()
    private val errorManager: ErrorManager by inject()

    private var isSingleAnswer: Boolean = true
    private var variantNumberOfSelectedRadioButton = 100
    private val variants = mutableListOf<TeacherTestQuestionVariantView>()
    private var question: TeacherTestQuestion? = null
    private var testId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question = arguments?.getParcelable(EXTRA_QUESTION)
        question?.let { setData(it) }
        testId = arguments?.getString(EXTRA_TEST_ID)

        configureSpinner(question?.isSingleQuestion)
        configureListeners()
        configureViewModel()
    }

    private fun setData(question: TeacherTestQuestion) {
        binding.question.setText(question.question)
        question.possibleAnswers.forEach { answer ->
            createVariant(question, answer.answer)
        }
    }

    private fun configureListeners() {
        binding.addQuestion.setOnClickListener {
            createVariant()
        }

        binding.save.setOnClickListener {
            val answers = getVariantsText()
            val questionText = binding.question.text.toString()
            val rightAnswers = getRightAnswers()

            val isValid = validateData(answers, questionText, rightAnswers)
            if (!isValid) return@setOnClickListener

            val newQuestion = TeacherTestQuestionBody(
                answers = answers,
                isSingleQuestion = isSingleAnswer,
                question = questionText,
                rightAnswers = rightAnswers
            )

            val question = question
            if (question != null) {
                viewModel.updateQuestion(question.id, newQuestion)
            }
            val testId = testId
            if (testId != null) {
                viewModel.createQuestion(testId, newQuestion)
            }
        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.createQuestionLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun createVariant(question: TeacherTestQuestion? = null, answer: String = "") {
        val variant = TeacherTestQuestionVariantView(requireContext())
        val variantNumber = variants.size + 1
        variant.setNumber(variantNumber)

        if (question != null) {
            variant.setText(answer)
            variant.setAnswersType(question.isSingleQuestion)

            val rightAnswersText = question.possibleAnswers
                .filter { possibleAnswer -> question.rightAnswers.contains(possibleAnswer.id) }
                .map { it.answer }

            rightAnswersText.forEach { text ->
                if (text == answer) {
                    if (question.isSingleQuestion) {
                        variantNumberOfSelectedRadioButton = variantNumber
                        variant.checkRadioButton()
                    } else {
                        variant.checkCheckBox()
                    }
                }
            }
        } else {
            variant.setAnswersType(isSingleAnswer)
        }

        variant.onDeleteClicked = { variantNum ->
            variants.removeAt(variantNum - 1)
            binding.variantLayout.removeViewAt(variantNum - 1)

            var newNumber = variantNum
            variants.forEach { variant ->
                val currentVariantNum = variant.getNumber()
                if (currentVariantNum > variantNum) {
                    variant.setNumber(newNumber)
                    newNumber += 1
                }
            }
        }

        variant.onRadioButtonSelected = { variantNum ->
            if (variantNumberOfSelectedRadioButton != variantNum ) {
                if (variantNumberOfSelectedRadioButton != 100) {
                    variants[variantNumberOfSelectedRadioButton - 1].uncheckRadioButton()
                }
                variantNumberOfSelectedRadioButton = variantNumber
            }
        }

        binding.variantLayout.addView(variant)
        variants.add(variant)
    }

    private fun getVariantsText(): List<String> {
        val answers = mutableListOf<String>()
        variants.forEach {
            answers.add(it.getText())
        }
        return answers
    }

    private fun getRightAnswers(): MutableList<Int> {
        val rightAnswers = mutableListOf<Int>()

        if (isSingleAnswer) {
            if (variantNumberOfSelectedRadioButton != 100) {
                rightAnswers.add(variantNumberOfSelectedRadioButton)
            }
        } else {
            variants.forEach { variant ->
                val variantNumber = variant.getVariantNumberIfCheckBoxIsChecked()
                if (variantNumber != null) {
                    rightAnswers.add(variantNumber)
                }
            }
        }

        return rightAnswers
    }

    private fun validateData(answers: List<String>, questionText: String, rightAnswers: MutableList<Int>): Boolean {
        if (questionText.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_add_question_empty_question_error))
            return false
        }
        if (answers.size < 2) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_add_question_few_answers_error))
            return false
        }
        answers.forEach { answer ->
            if (answer.isBlank() && answers.size >= 2) {
                errorManager.showError(ErrorManagerError.ResError(R.string.teacher_add_question_empty_answer_error))
                return false
            }
        }
        if (rightAnswers.isEmpty() && answers.size >= 2) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_add_question_empty_right_answer_error))
            return false
        }

        return true
    }

    private fun configureSpinner(singleQuestion: Boolean?) {
        val spinnerOptions = resources.getStringArray(R.array.teacher_add_question_answers_quantity_spinner)
        spinnerAdapter = ArrayAdapter(binding.answersQuantitySpinner.context, android.R.layout.simple_list_item_1, spinnerOptions)
        binding.answersQuantitySpinner.adapter = spinnerAdapter

        if (singleQuestion != null && singleQuestion == false) {
            binding.answersQuantitySpinner.setSelection(1)
        } else {
            binding.answersQuantitySpinner.setSelection(0)
        }

        binding.answersQuantitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = binding.answersQuantitySpinner.selectedItem
                if (selectedItem is String) {
                    val singleAnswerString = resources.getStringArray(R.array.teacher_add_question_answers_quantity_spinner)[0]
                    isSingleAnswer = selectedItem == singleAnswerString
                    variants.forEach { variant ->
                        variant.setAnswersType(isSingleAnswer)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    companion object {
        private const val EXTRA_QUESTION = "extra_question"
        private const val EXTRA_TEST_ID = "extra_test_id"

        fun getNewInstance(testId: String, question: TeacherTestQuestion?): TeacherTestQuestionFragment {
            return TeacherTestQuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TEST_ID, testId)
                    putParcelable(EXTRA_QUESTION, question)
                }
            }
        }
    }
}