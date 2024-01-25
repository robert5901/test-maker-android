package com.example.testmaker.ui.teacher.results.testList.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.test.Test
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.TeacherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherResultsTestListViewModel(
    private val repository: TeacherRepository,
    private val errorManager: ErrorManager
): ViewModel() {
    private val _allTestsLoading = MutableStateFlow(false)
    val allTestsLoading = _allTestsLoading.asStateFlow()

    private val _allTests = MutableStateFlow<List<Test>?>(null)
    val allTests = _allTests.asStateFlow()

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
}