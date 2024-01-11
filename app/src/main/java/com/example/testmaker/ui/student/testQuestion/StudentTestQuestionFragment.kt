package com.example.testmaker.ui.student.testQuestion

import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.testmaker.R
import com.example.testmaker.core.Action
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.FragmentStudentTestQuestionBinding
import com.example.testmaker.models.student.StudentAnswer
import com.example.testmaker.models.student.StudentTestQuestion
import com.example.testmaker.models.student.StudentTestStart
import com.example.testmaker.ui.student.testQuestion.viewModels.StudentTestQuestionViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class StudentTestQuestionFragment: Fragment(R.layout.fragment_student_test_question) {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var test: StudentTestStart

    private val binding by viewBinding(FragmentStudentTestQuestionBinding::bind)
    private val errorManager: ErrorManager by inject()
    private val viewModel: StudentTestQuestionViewModel by inject()
    private val router: Router by inject()
    private val margin by lazy { resources.getDimensionPixelSize(R.dimen.student_test_question_radio_button_margin_bottom) }
    private val textSize by lazy { resources.getDimension(R.dimen.student_test_question_radio_button_text_size) }

    private val radioButtonList: MutableList<RadioButton> = mutableListOf()
    private val checkBoxList: MutableList<CheckBox> = mutableListOf()
    private val studentAnswers: MutableList<StudentAnswer> = mutableListOf()
    private var currentQuestionIndex = 0
    private var indexOfSelectedRadioButton = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) { }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        test = arguments?.getParcelable(EXTRA_TEST) ?: return
        setData(test)

        configureListeners()
    }

    override fun onStop() {
        // TODO сможет ли запрос отработать если свернуть прилу?
        saveAnswer()
        viewModel.finishTest(studentAnswers)

        countDownTimer.cancel()

        super.onStop()
    }


    private fun setData(test: StudentTestStart) {
        val question = test.questions[currentQuestionIndex]
        setQuestion(question)
        binding.next.isVisible = true
        binding.finish.isVisible = false

        startTimer(test.doneTime)
    }

    private fun configureListeners() {
        binding.close.setOnClickListener {
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.student_test_question_close_alert),
                actionTitle = resources.getString(R.string.common_ok),
                action = {
                    saveAnswer()
                    viewModel.finishTest(studentAnswers)
                }
            )
        }

        binding.next.setOnClickListener {
            saveAnswer { isSuccess ->
                if (isSuccess) {
                    currentQuestionIndex += 1
                    setQuestion(test.questions[currentQuestionIndex])
                }
            }
        }

        binding.previous.setOnClickListener {
            currentQuestionIndex -= 1
            setQuestion(test.questions[currentQuestionIndex])
        }

        binding.finish.setOnClickListener {
            saveAnswer()

            viewModel.finishTest(studentAnswers)
        }
    }

    private fun setQuestion(question: StudentTestQuestion) {
        if (currentQuestionIndex == 0) {
            binding.previous.visibility = View.INVISIBLE
        } else {
            binding.previous.visibility = View.VISIBLE
        }
        binding.next.isVisible = currentQuestionIndex + 1 != test.questions.size
        binding.finish.isVisible = currentQuestionIndex + 1 == test.questions.size
        binding.image.isVisible = !question.imageUrl.isNullOrBlank()

        binding.question.text = resources.getString(R.string.student_test_question_question_text, currentQuestionIndex+1, question.question)
        if (!question.imageUrl.isNullOrBlank()) {
            Glide.with(requireContext())
                .load(question.imageUrl)
                .centerCrop()
                .into(binding.image)
        }

        binding.answersLayout.removeAllViews()
        radioButtonList.clear()
        checkBoxList.clear()

        question.possibleAnswers.forEach { answer ->
            if (question.isSingleAnswer) {
                createRadioButton(answer.answer)
            } else {
                createCheckBox(answer.answer)
            }
        }

        // Выбор вариантов ответа, если уже выбрали в этом вопросе
        val existingAnswers = studentAnswers.find { it.questionId == question.id }?.answers

        if (!existingAnswers.isNullOrEmpty()) {
            if (question.isSingleAnswer) {
                val selectedAnswerId = existingAnswers.first()
                val selectedAnswerIndex = question.possibleAnswers.indexOfFirst { it.id == selectedAnswerId }

                if (selectedAnswerIndex != -1) {
                    radioButtonList.getOrNull(selectedAnswerIndex)?.isChecked = true
                }
            } else {
                question.possibleAnswers.forEachIndexed { index, answer ->
                    val isChecked = existingAnswers.contains(answer.id)
                    checkBoxList.getOrNull(index)?.isChecked = isChecked
                }
            }
        } else {
            // В остальных случаях снимаем состояние всех радиокнопок и чекбоксов
            if (question.isSingleAnswer) {
                radioButtonList.forEach { it.isChecked = false }
            } else {
                checkBoxList.forEach { it.isChecked = false }
            }
        }
    }

    private fun createRadioButton(answerText: String) {
        val radioButton = RadioButton(requireContext())
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, margin)
        radioButton.layoutParams = params
        radioButton.text = answerText
        radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

        radioButtonList.add(radioButton)

        radioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val selectedRadioButtonIndex = radioButtonList.indexOf(buttonView as RadioButton)

                if (indexOfSelectedRadioButton != selectedRadioButtonIndex) {
                    if (indexOfSelectedRadioButton != 100) {
                        radioButtonList[indexOfSelectedRadioButton].isChecked = false
                    }
                    indexOfSelectedRadioButton = selectedRadioButtonIndex
                }
            }
        }

        binding.answersLayout.addView(radioButton)
    }

    private fun createCheckBox(answerText: String) {
        val checkBox = CheckBox(requireContext())
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, margin)
        checkBox.layoutParams = params
        checkBox.text = answerText
        checkBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

        binding.answersLayout.addView(checkBox)
        checkBoxList.add(checkBox)
    }

    private fun saveAnswer(isSuccess: Action<Boolean>? = null) {
        val currentQuestion = test.questions[currentQuestionIndex]
        val questionAnswers = currentQuestion.possibleAnswers

        val selectedAnswers = if (currentQuestion.isSingleAnswer) {
            if (indexOfSelectedRadioButton != 100) {
                listOfNotNull(
                    questionAnswers.find { it.answer == radioButtonList[indexOfSelectedRadioButton].text }?.id
                )
            } else {
                emptyList()
            }
        } else {
            checkBoxList.filter { it.isChecked }.mapNotNull { checkBox ->
                questionAnswers.find { it.answer == checkBox.text }?.id
            }
        }

        if (selectedAnswers.isEmpty()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.student_test_question_right_answer_error))
            isSuccess?.invoke(false)
            return
        }

        val existingAnswerIndex = studentAnswers.indexOfFirst { it.questionId == currentQuestion.id }
        if (existingAnswerIndex != -1) {
            studentAnswers[existingAnswerIndex] = studentAnswers[existingAnswerIndex].copy(answers = selectedAnswers)
        } else {
            studentAnswers.add(StudentAnswer(currentQuestion.id, selectedAnswers))
        }
        indexOfSelectedRadioButton = 100
        isSuccess?.invoke(true)
    }

    private fun startTimer(doneTime: String) {
        // TODO рассчет времени для таймера. doneTime - currentTime = 20мин(например)
        val totalTimeInMillis = 3000
        binding.progressBar.max = totalTimeInMillis

        countDownTimer = object : CountDownTimer(totalTimeInMillis.toLong(), 10) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBar.progress = (totalTimeInMillis - millisUntilFinished).toInt()
            }

            override fun onFinish() {
                // TODO finish test. save results
            }
        }.start()
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