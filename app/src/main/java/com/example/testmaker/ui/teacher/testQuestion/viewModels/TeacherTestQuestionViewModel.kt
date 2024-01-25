package com.example.testmaker.ui.teacher.testQuestion.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.teacher.TeacherTestQuestionBody
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.TeacherRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherTestQuestionViewModel(
    private val repository: TeacherRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val _createQuestionLoading = MutableStateFlow(false)
    val createQuestionLoading = _createQuestionLoading.asStateFlow()

    fun createQuestion(testId: String, question: TeacherTestQuestionBody) {
        viewModelScope.launch {
            _createQuestionLoading.emit(true)

            val response = repository.createQuestion(testId, question)
            _createQuestionLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    router.navigateTo(TeacherScreens.testQuestionListScreen(response.data))
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun updateQuestion(testId: String, questionId: String, question: TeacherTestQuestionBody) {
        viewModelScope.launch {
            _createQuestionLoading.emit(true)

            val response = repository.changeQuestion(testId, questionId, question)
            _createQuestionLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    router.navigateTo(TeacherScreens.testQuestionListScreen(response.data))
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}