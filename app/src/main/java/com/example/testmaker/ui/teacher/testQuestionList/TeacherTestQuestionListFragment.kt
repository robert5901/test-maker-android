package com.example.testmaker.ui.teacher.testQuestionList

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.testmaker.R
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.core.utils.extensions.showAlertMessageWithNegativeButton
import com.example.testmaker.databinding.AddImageDialogFragmentBinding
import com.example.testmaker.databinding.FragmentTeacherTestQuestionListBinding
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.ui.teacher.testQuestionList.adapters.TeacherTestQuestionListAdapter
import com.example.testmaker.ui.teacher.testQuestionList.viewModels.TeacherTestQuestionListViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import java.io.IOException

class TeacherTestQuestionListFragment : Fragment(R.layout.fragment_teacher_test_question_list) {
    private lateinit var adapter: TeacherTestQuestionListAdapter

    private val binding by viewBinding(FragmentTeacherTestQuestionListBinding::bind)
    private val viewModel: TeacherTestQuestionListViewModel by inject()
    private val errorManager: ErrorManager by inject()
    private val router: Router by inject()

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            try {
                viewModel.saveQuestionImage(uri)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.teacher_test_question_close_dialog_message),
                actionTitle = resources.getString(R.string.teacher_test_question_close_dialog_action),
                action = { router.backTo(TeacherScreens.teacherScreen()) }
            )
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureListeners()
        configureViewModel()
        configureAdapter()

        val test: TeacherTest? = arguments?.getParcelable(EXTRA_TEST)
        if (test != null) {
            viewModel.setTest(test)
        }
    }

    private fun configureListeners() {
        binding.saveName.setOnClickListener {
            val name = binding.name.text.toString()
            if (name.isBlank()) {
                errorManager.showError(ErrorManagerError.ResError(R.string.teacher_test_question_name_error))
                return@setOnClickListener
            }
            viewModel.saveName(name)
        }

        binding.addQuestion.setOnClickListener {
            if (!hasName()) return@setOnClickListener
            val testId = viewModel.test.value?.id ?: return@setOnClickListener
            router.navigateTo(TeacherScreens.addQuestionScreen(testId))
        }

        binding.saveTest.setOnClickListener {
            if (!hasName()) return@setOnClickListener

            router.exit()
        }

        binding.copyTest.setOnClickListener {
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.teacher_configure_test_copy_alert_message),
                actionTitle = resources.getString(R.string.common_ok),
                action = {
                    Toast.makeText(requireContext(), "Копия теста успешно создана", Toast.LENGTH_SHORT).show()
                    router.navigateTo(TeacherScreens.teacherScreen())
                }
            )
        }
    }

    private fun configureAdapter() {
        adapter = TeacherTestQuestionListAdapter()

        adapter.onChangeClicked = { question ->
            val testId = viewModel.test.value?.id
            if (testId != null) {
                router.navigateTo(TeacherScreens.addQuestionScreen(testId, question))
            }
        }
        adapter.onDeleteClicked = { question ->
            showAlertMessageWithNegativeButton(requireContext(),
                title = resources.getString(R.string.common_attention),
                message = resources.getString(R.string.teacher_test_question_delete_question),
                actionTitle = resources.getString(R.string.common_delete),
                action = { viewModel.deleteQuestion(question.id) }
            )
        }
        adapter.onAddImageClicked = { question ->
            openAddImageDialog(question.imageUrl, question.id)
        }

        binding.recyclerView.adapter = adapter
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
        observeOnStarted(viewModel.test) { test ->
            if (test == null) return@observeOnStarted
            setTestInfo(test)

            // test data
            if (!test.questions.isNullOrEmpty()) {
                binding.points.text = resources.getString(R.string.teacher_test_question_points, 4)
            }
        }
    }

    private fun setTestInfo(test: TeacherTest) {
        binding.name.setText(test.name)
        adapter.set(test.questions ?: emptyList())
    }

    private fun hasName(): Boolean {
        if (viewModel.test.value?.name.isNullOrBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.teacher_test_question_save_name_error))
            return false
        }
        return true
    }

    private fun openAddImageDialog(imageUrl: String?, questionId: String) {
        val dialogBinding = AddImageDialogFragmentBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (imageUrl != null) {
            Glide.with(requireContext())
                .load(imageUrl)
                .into(dialogBinding.image)
        } else {
            dialogBinding.image.setImageDrawable(null)
        }

        dialogBinding.deleteImage.setOnClickListener {
            viewModel.deleteQuestionImage(questionId)
            dialog.cancel()
        }

        dialogBinding.addImage.setOnClickListener {
            selectImageLauncher.launch("image/*")
            dialog.cancel()
        }

        dialog.show()
    }

    companion object {
        private const val EXTRA_TEST = "extra_test"

        fun getNewInstance(test: TeacherTest): TeacherTestQuestionListFragment {
            return TeacherTestQuestionListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_TEST, test)
                }
            }
        }
    }
}