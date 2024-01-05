package com.example.testmaker.ui.teacher.testQuestionList

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
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.AddImageDialogFragmentBinding
import com.example.testmaker.databinding.FragmentTeacherTestQuestionListBinding
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.ui.TestTeacherTest
import com.example.testmaker.ui.teacher.testQuestionList.adapters.TeacherTestQuestionListAdapter
import com.example.testmaker.ui.teacher.testQuestionList.viewModels.TeacherTestQuestionListViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import java.io.IOException

class TeacherTestQuestionListFragment : Fragment(R.layout.fragment_teacher_test_question_list) {
    private lateinit var adapter: TeacherTestQuestionListAdapter

    private val binding by viewBinding(FragmentTeacherTestQuestionListBinding::bind)
    private val viewModel: TeacherTestQuestionListViewModel by inject()
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

        configureListeners()
        configureAdapter()

        val testId = arguments?.getString(EXTRA_TEST_ID) ?: return
        val test: TeacherTest? = arguments?.getParcelable(EXTRA_TEST)
        if (test == null) {
            viewModel.setTestId(testId)
        } else {
            setTestInfo(test)
        }

        adapter.set(
            // TODO test data
            TestTeacherTest.teacherTest.question ?: return
        )
    }

    private fun configureAdapter() {
        adapter = TeacherTestQuestionListAdapter()

        adapter.onChangeClicked = { question ->
            router.navigateTo(TeacherScreens.addQuestionScreen(question))
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
            router.navigateTo(TeacherScreens.addQuestionScreen())
        }

        binding.saveTest.setOnClickListener {
            if (!hasName()) return@setOnClickListener

//            viewModel.saveTest()
        }
    }

    private fun setTestInfo(test: TeacherTest) {
        binding.name.setText(test.name)
        adapter.set(test.question ?: emptyList())
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

    companion object {
        // TODO передавать test и выставлять список вопросов
        private const val EXTRA_TEST_ID = "extra_testId"
        private const val EXTRA_TEST = "extra_test"

        fun getNewInstance(testId: String, test: TeacherTest?): TeacherTestQuestionListFragment {
            return TeacherTestQuestionListFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TEST_ID, testId)
                    putParcelable(EXTRA_TEST, test)
                }
            }
        }
    }
}