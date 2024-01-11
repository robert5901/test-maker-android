package com.example.testmaker.ui.teacher.testQuestionList.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.models.teacher.TeacherTestQuestionBody
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.TeacherRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TeacherTestQuestionListViewModel(
    private val repository: TeacherRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val _saveNameLoading = MutableStateFlow(false)
    private val _deleteQuestionLoading = MutableStateFlow(false)

    private val _createQuestion = MutableStateFlow(false)

    private val _test = MutableStateFlow<TeacherTest?>(null)
    val test = _test.asStateFlow()

    val loading = combine(
        _saveNameLoading,
        _deleteQuestionLoading
    ) { saveNameLoading, deleteQuestionLoading ->
        saveNameLoading || deleteQuestionLoading
    }

    fun setTest(test: TeacherTest) {
        if (_test.value == null) {
            _test.tryEmit(test)
        }
    }

    fun saveName(name: String) {
        viewModelScope.launch {
            val testId = _test.value?.id ?: return@launch
            _saveNameLoading.emit(true)

            val response = repository.saveName(testId, name)
            _saveNameLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _test.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun deleteQuestion(questionId: String) {
        viewModelScope.launch {
            val testId = _test.value?.id ?: return@launch
            _deleteQuestionLoading.emit(true)

            val response = repository.deleteQuestion(testId, questionId)
            _deleteQuestionLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _test.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun saveQuestionImage(fileUri: Uri) {
        viewModelScope.launch {
            when (val response = repository.addImage(fileUri)) {
                is ApiResponse.Success -> {
                    _test.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun deleteQuestionImage(questionId: String) {
        viewModelScope.launch {
            val testId = _test.value?.id ?: return@launch
            when (val response = repository.deleteImage(testId, questionId)) {
                is ApiResponse.Success -> {
                    _test.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun createQuestion(question: TeacherTestQuestionBody) {
        viewModelScope.launch {
            val testId = _test.value?.id ?: return@launch
            _createQuestion.emit(true)

            val response = repository.createQuestion(testId, question)
            _createQuestion.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _test.emit(response.data)
                    router.exit()
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun updateQuestion(questionId: String, question: TeacherTestQuestionBody) {

    }
}