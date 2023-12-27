package com.example.testmaker.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.AdminScreens
import com.example.testmaker.AuthScreens
import com.example.testmaker.R
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentLoginBinding
import com.example.testmaker.models.auth.AuthLogin
import com.example.testmaker.models.auth.UserRole
import com.example.testmaker.ui.auth.viewModels.AuthViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val errorManager: ErrorManager by inject()
    private val viewModel: AuthViewModel by inject()
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()

        binding.registration.setOnClickListener {
            router.navigateTo(AuthScreens.registrationScreen())
        }

        binding.login.setOnClickListener {
            // TODO test data
//            login()
            when (binding.password.text.toString()) {
                "1" -> {router.replaceScreen(AdminScreens.adminScreen())}
                "2" -> {router.replaceScreen(TeacherScreens.teacherScreen())}
            }

        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.email.isEnabled = !isLoading
            binding.password.isEnabled = !isLoading
            binding.login.isEnabled = !isLoading
            binding.registration.isEnabled = !isLoading
        }

        observeOnStarted(viewModel.authInfo) { authInfo ->
            // TODO что делать с access и refresh

            if (authInfo == null) return@observeOnStarted

            when (authInfo.userRole) {
                UserRole.ADMIN -> {
                    router.replaceScreen(AdminScreens.adminScreen())
                }
                UserRole.STUDENT -> {
//                    router.replaceScreen(StudentScreens.studentScreen())
                }
                UserRole.TEACHER -> {
                    router.replaceScreen(TeacherScreens.teacherScreen())
                }
            }
        }
    }

    private fun login() {
        val isValid = validateEditTexts()
        if (!isValid) return

        val login = AuthLogin(
            binding.email.text.toString(),
            binding.password.text.toString()
        )

        viewModel.login(login)
    }

    private fun validateEditTexts(): Boolean {
        // TODO проверка на длину пароля? на корректный e-mail?
        var isAllFieldsValid = true

        if (binding.email.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.auth_email_error))
            isAllFieldsValid = false
        }
        if (binding.password.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.auth_password_error))
            isAllFieldsValid = false
        }
        return isAllFieldsValid
    }
}