package com.example.testmaker.ui.teacher.configureTest.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.student.Group
import com.example.testmaker.models.test.ConfigureTestBody
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.GroupRepository
import com.example.testmaker.network.repositories.TeacherRepository
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TeacherTestConfigureViewModel(
    private val repository: TeacherRepository,
    private val groupRepository: GroupRepository,
    private val errorManager: ErrorManager,
    private val router: Router
): ViewModel() {
    private val _groupsLoading = MutableStateFlow(false)
    private val _saveConfigLoading = MutableStateFlow(false)

    val loading = combine(_saveConfigLoading, _groupsLoading) { saveConfigLoading, groupsLoading ->
        saveConfigLoading || groupsLoading
    }

    private val _groups = MutableStateFlow<List<Group>?>(null)
    val groups = _groups.asStateFlow()

    fun saveConfig(testId: String, configTestBody: ConfigureTestBody) {
        viewModelScope.launch {
            _saveConfigLoading.emit(true)

            val response = repository.saveConfig(testId, configTestBody)
            _saveConfigLoading.emit(false)

            when (response) {
                is ApiResponse.Success -> {
                    router.exit()
                }
                is ApiResponse.Error -> {
                    errorManager.showError(response.errorManagerError)
                }
            }
        }
    }

    fun getGroups() {
        viewModelScope.launch {
            _groupsLoading.emit(true)

            val response = groupRepository.getGroups()
            _groupsLoading.emit(false)

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
}