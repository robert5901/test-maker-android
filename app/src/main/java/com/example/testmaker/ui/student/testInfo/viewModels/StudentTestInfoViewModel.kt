package com.example.testmaker.ui.student.testInfo.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.StudentScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.student.StudentTestStart
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.StudentRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentTestInfoViewModel(
    private val repository: StudentRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val _test = MutableStateFlow<StudentTestStart?>(null)
    val test = _test.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun startTest(testId: String) {
        viewModelScope.launch {
            _loading.emit(true)

            val response = repository.startTest(testId)
            _loading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    router.navigateTo(StudentScreens.testQuestionScreen(response.data))
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}