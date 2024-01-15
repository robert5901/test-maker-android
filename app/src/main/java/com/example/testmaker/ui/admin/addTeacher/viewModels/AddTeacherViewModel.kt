package com.example.testmaker.ui.admin.addTeacher.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.AdminScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.admin.TeacherBody
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.AdminRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AddTeacherViewModel(
    private val repository: AdminRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val _changeTeacherLoading = MutableStateFlow(false)
    private val _registerTeacherLoading = MutableStateFlow(false)

    val loading = combine(
        _changeTeacherLoading,
        _registerTeacherLoading
    ) { changeTeacherLoading, registerTeacherLoading ->
        changeTeacherLoading || registerTeacherLoading
    }

    fun changeTeacher(id: String, teacherBody: TeacherBody) {
        viewModelScope.launch {
            _changeTeacherLoading.emit(true)

            val response = repository.changeTeacher(id, teacherBody)
            _changeTeacherLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    router.navigateTo(AdminScreens.adminScreen())
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun registrationTeacher(teacherBody: TeacherBody) {
        viewModelScope.launch {
            _registerTeacherLoading.emit(true)

            val response = repository.registerTeacher(teacherBody)
            _registerTeacherLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    router.navigateTo(AdminScreens.adminScreen())
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}