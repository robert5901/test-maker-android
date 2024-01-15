package com.example.testmaker.ui.student.testList.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.student.StudentTest
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentTestListViewModel(
    private val repository: StudentRepository,
    private val errorManager: ErrorManager
): ViewModel() {
    // TODO добавить refresh свайпом для обновления списка тестов

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _tests = MutableStateFlow<List<StudentTest>?>(null)
    val tests = _tests.asStateFlow()

    fun getTests() {
        viewModelScope.launch {
            _loading.emit(true)

            val response = repository.getTests()
            _loading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _tests.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}