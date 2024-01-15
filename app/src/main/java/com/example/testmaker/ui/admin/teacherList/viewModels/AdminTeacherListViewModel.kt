package com.example.testmaker.ui.admin.teacherList.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.users.Teacher
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AdminTeacherListViewModel(
    private val repository: AdminRepository,
    private val errorManager: ErrorManager
): ViewModel() {
    private val _deleteTeacherLoading = MutableStateFlow(false)
    private val _teachersLoading = MutableStateFlow(false)

    val loading = combine(
        _deleteTeacherLoading,
        _teachersLoading
    ) { deleteTeacherLoading, teacherLoading ->
        deleteTeacherLoading || teacherLoading
    }

    private val _teachers = MutableStateFlow<List<Teacher>>(emptyList())
    val teachers = _teachers.asStateFlow()

    fun updateTeachers() {
        viewModelScope.launch {
            _teachersLoading.emit(true)

            val response = repository.updateTeachers()
            _teachersLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _teachers.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun deleteTeacher(id: String) {
        viewModelScope.launch {
            _deleteTeacherLoading.emit(true)

            val response = repository.delete(id)
            _deleteTeacherLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    updateTeachers()
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}