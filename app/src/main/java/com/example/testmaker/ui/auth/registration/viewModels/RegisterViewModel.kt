package com.example.testmaker.ui.auth.registration.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.AuthScreens
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.users.Student
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.AuthRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val groupsLoading = MutableStateFlow(false)
    private val createStudentLoading = MutableStateFlow(false)

    private val _groups = MutableStateFlow<List<Group>?>(null)
    val groups = _groups.asStateFlow()

    val loading = combine(groupsLoading, createStudentLoading) { groupsLoading, createLoading ->
        groupsLoading || createLoading
    }

    fun init() {
        getGroups()
    }

    private fun getGroups() {
        viewModelScope.launch {
            groupsLoading.emit(true)

            val response = authRepository.getGroups()
            groupsLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    _groups.emit(response.data)
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun createStudent(student: Student) {
        viewModelScope.launch {
            createStudentLoading.emit(true)

            val response = authRepository.createStudent(student.groupId, student.login, student.name, student.password)
            createStudentLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    // TODO redirect to student screen
                    router.navigateTo(AuthScreens.loginScreen())
                }

                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }
}