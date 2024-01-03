package com.example.testmaker.ui.teacher.createTest

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.AddImageDialogFragmentBinding
import com.example.testmaker.databinding.FragmentTeacherCreateTestBinding
import com.example.testmaker.models.teacher.TeacherTestQuestion
import com.example.testmaker.models.test.Answer
import com.example.testmaker.ui.teacher.createTest.adapters.TeacherCreateTestQuestionsAdapter
import com.example.testmaker.ui.teacher.createTest.viewModels.TeacherCreateTestViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import java.io.IOException

class TeacherCreateTestFragment : Fragment(R.layout.fragment_teacher_create_test) {
    private lateinit var adapter: TeacherCreateTestQuestionsAdapter

    private val binding by viewBinding(FragmentTeacherCreateTestBinding::bind)
    private val viewModel: TeacherCreateTestViewModel by inject()
    private val router: Router by inject()

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            try {
                // TODO передавать id вопроса? мб во viewModel это можно перенести?
                viewModel.saveQuestionImage(uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testId = arguments?.getString(EXTRA_TEST_ID) ?: return
        viewModel.setTestId(testId)

        configureListeners()
        configureAdapter()

        adapter.set(
            // TODO test data
            getQuestions()
        )
    }

    private fun configureAdapter() {
        adapter = TeacherCreateTestQuestionsAdapter()

        adapter.onChangeClicked = { question ->
//            router.navigateTo(TeacherScreens.addQuestion())
        }
        adapter.onDeleteClicked = { question ->
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.teacher_create_test_delete_question),
                actionTitle = resources.getString(R.string.common_delete),
//                action = { viewModel.deleteQuestion(question.id) }
            )
        }
        adapter.onAddImageClicked = { question ->
            openAddImageDialog(question.imageUrl)
        }

        binding.recyclerView.adapter = adapter
    }

    private fun configureListeners() {
        binding.saveName.setOnClickListener {
            // TODO выставлять имя теста если сохранили в editText
//            viewModel.saveName(binding.name.text.toString())
        }

        binding.addQuestion.setOnClickListener {
            if (!hasName()) return@setOnClickListener
//                router.navigateTo(TeacherScreens.addQuestion())
        }

        binding.saveTest.setOnClickListener {
            if (!hasName()) return@setOnClickListener

//            viewModel.saveTest()
        }
    }

    private fun hasName(): Boolean {
        // TODO проверять есть ли name в тесте из viewModel и выводить ошибку если нет
        return true
    }

    private fun openAddImageDialog(imageUrl: String?) {
        val dialogBinding = AddImageDialogFragmentBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (imageUrl != null) {
            Glide.with(requireContext())
                .load(imageUrl)
                .centerCrop()
                .into(dialogBinding.image)
        } else {
            dialogBinding.image.setImageDrawable(null)
        }

        dialogBinding.deleteImage.setOnClickListener {
//            viewModel.deleteImage(testId)
        }

        dialogBinding.addImage.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        dialog.show()
    }

    // TODO test data
    private fun getQuestions(): List<TeacherTestQuestion> {
        return listOf(
            TeacherTestQuestion(
                "1", "https://i.pinimg.com/originals/03/ab/0d/03ab0d21c9d9f2210e774a8b584ef962.png",
                true,
                listOf(
                    Answer("1", "ответ 1"),
                    Answer("2", "ответ 2"),
                    Answer("3", "ответ 3"),
                    Answer("4", "ответ 4")
                ), "Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса",
                listOf("1")
            ),
            TeacherTestQuestion(
                "2", "",
                false,
                listOf(
                    Answer("1", "ответ 1"),
                    Answer("2", "ответ 2"),
                    Answer("3", "ответ 3"),
                    Answer("4", "ответ 4")
                ), "Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса Текст вопроса",
                listOf("2")
            )
        )
    }

    companion object {
        private const val EXTRA_TEST_ID = "extra_testId"

        fun getNewInstance(testId: String): TeacherCreateTestFragment {
            return TeacherCreateTestFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TEST_ID, testId)
                }
            }
        }
    }
}