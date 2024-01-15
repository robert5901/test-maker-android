package com.example.testmaker.ui.student.testQuestion.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.StudentScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.student.StudentAnswer
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.StudentRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentTestQuestionViewModel(
    private val repository: StudentRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun finishTest(studentAnswers: List<StudentAnswer>) {
        viewModelScope.launch {
            _loading.emit(true)

            val response = repository.finishTest(studentAnswers)
            _loading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    router.navigateTo(StudentScreens.testPassResult(response.data))
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}