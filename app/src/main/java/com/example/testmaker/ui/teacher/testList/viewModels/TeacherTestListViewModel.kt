package com.example.testmaker.ui.teacher.testList.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.TeacherScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.test.Test
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.TeacherRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TeacherTestListViewModel(
    private val repository: TeacherRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val _testLoading = MutableStateFlow(false)
    private val _allTestsLoading = MutableStateFlow(false)
    private val _deleteTestLoading = MutableStateFlow(false)
    private val _createTestLoading = MutableStateFlow(false)

    private val _allTests = MutableStateFlow<List<Test>?>(null)
    val allTests = _allTests.asStateFlow()

    val loading = combine(
        _testLoading,
        _allTestsLoading,
        _deleteTestLoading,
        _createTestLoading
    ) { testLoading, allTestsLoading, deleteTestLoading, createTestLoading ->
        testLoading || allTestsLoading || deleteTestLoading || createTestLoading
    }

    fun getAllTests() {
        viewModelScope.launch {
            _allTestsLoading.emit(true)

            val response = repository.getAllTests()
            _allTestsLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _allTests.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun getTeacherTest(testId: String) {
        viewModelScope.launch {
            _testLoading.emit(true)

            val response = repository.getTest(testId)
            _testLoading.emit(false)

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

    fun deleteTest(testId: String) {
        viewModelScope.launch {
            _deleteTestLoading.emit(true)

            val response = repository.deleteTest(testId)
            _deleteTestLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _allTests.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun createTest() {
        viewModelScope.launch {
            _createTestLoading.emit(true)

            val response = repository.createTest()
            _createTestLoading.emit(false)

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