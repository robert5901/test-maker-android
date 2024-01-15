package com.example.testmaker.ui.teacher.results.testResults.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.teacher.StudentTestResult
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.TeacherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherTestResultsViewModel(
    private val repository: TeacherRepository,
    private val errorManager: ErrorManager
): ViewModel() {
    private val _resultsLoading = MutableStateFlow(false)
    val resultsLoading = _resultsLoading.asStateFlow()

    private val _results = MutableStateFlow<List<StudentTestResult>?>(null)
    val results = _results.asStateFlow()

    fun getResults(testId: String) {
        viewModelScope.launch {
            _resultsLoading.emit(true)

            val response = repository.getResults(testId)
            _resultsLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _results.emit(response.data.sortedBy { it.studentGroup.title })
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}