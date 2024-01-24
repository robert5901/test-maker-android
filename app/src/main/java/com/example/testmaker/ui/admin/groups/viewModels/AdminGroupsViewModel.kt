package com.example.testmaker.ui.admin.groups.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.models.student.Group
import com.example.testmaker.network.models.ApiResponse
import com.example.testmaker.network.repositories.GroupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AdminGroupsViewModel(
    private val repository: GroupRepository,
    private val errorManager: ErrorManager
): ViewModel() {
    private val _groupsLoading = MutableStateFlow(false)
    private val _addGroupLoading = MutableStateFlow(false)

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()

    val loading = combine(_groupsLoading, _addGroupLoading) { groupsLoading, addGroupLoading ->
        groupsLoading || addGroupLoading
    }

    fun updateGroups() {
        viewModelScope.launch {
            _groupsLoading.emit(true)

            val response = repository.getGroups()
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

    fun deleteGroup(id: String) {
        viewModelScope.launch {
            _groupsLoading.emit(true)

            val response = repository.deleteGroup(id)
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

    fun addGroup(name: String) {
        viewModelScope.launch {
            _groupsLoading.emit(true)

            val response = repository.addGroup(name)
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