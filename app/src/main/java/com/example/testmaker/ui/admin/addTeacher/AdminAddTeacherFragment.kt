package com.example.testmaker.ui.admin.addTeacher

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentAdminAddTeacherBinding
import com.example.testmaker.models.admin.Teacher
import com.example.testmaker.models.admin.TeacherWithPassword
import com.example.testmaker.ui.admin.addTeacher.viewModels.AddTeacherViewModel
import org.koin.android.ext.android.inject
import java.security.SecureRandom

class AdminAddTeacherFragment: Fragment(R.layout.fragment_admin_add_teacher) {
    private val binding by viewBinding(FragmentAdminAddTeacherBinding::bind)
    private val viewModel: AddTeacherViewModel by inject()
    private val errorManager: ErrorManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO deprecated
        val teacher: Teacher? = arguments?.getParcelable(EXTRA_TEACHER)

        binding.generatePassword.setOnClickListener {
            binding.password.setText(generatePassword())
        }

        binding.actionButton.setOnClickListener {
            if (teacher != null) {
                getTeacherWithPassword(teacher.id)
            } else {
                getTeacherWithPassword()
            }
        }

        setData(teacher)

        configureViewModel()
    }

    private fun setData(teacher: Teacher?) {
        binding.login.setText(teacher?.login)
        binding.name.setText(teacher?.name)

        val buttonText = if (teacher != null) {
            resources.getString(R.string.admin_add_teacher_change)
        } else {
            resources.getString(R.string.admin_add_teacher_create)
        }
        binding.actionButton.setButtonText(buttonText)
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) {
            binding.progressBar.isVisible = it
        }
    }

    private fun generatePassword(): String {
        val charset = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val secureRandom = SecureRandom()
        return (1..8)
            .map { charset[secureRandom.nextInt(charset.size)] }
            .joinToString("")
    }

    private fun getTeacherWithPassword(id: String? = null) {
        val isValid = validateEditTexts()
        if (!isValid) return

        val teacher = TeacherWithPassword(
            id,
            binding.name.text.toString(),
            binding.login.text.toString(),
            binding.password.text.toString()
        )

        if (id != null) {
            viewModel.changeTeacher(teacher)
        } else {
            viewModel.registrationTeacher(teacher)
        }
    }

    private fun validateEditTexts(): Boolean {
        // TODO проверка на длину пароля? на корректный ФИО?
        var isAllFieldsValid = true

        if (binding.name.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.admin_add_teacher_name_error))
            isAllFieldsValid = false
        }
        if (binding.login.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.admin_add_teacher_login_error))
            isAllFieldsValid = false
        }
        if (binding.password.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.admin_add_teacher_password_error))
            isAllFieldsValid = false
        }
        return isAllFieldsValid
    }

    companion object {
        private const val EXTRA_TEACHER = "extra_teacher"

        fun getNewInstance(teacher: Teacher?): AdminAddTeacherFragment {
            if (teacher == null) return AdminAddTeacherFragment()

            return AdminAddTeacherFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_TEACHER, teacher)
                }
            }
        }
    }
}