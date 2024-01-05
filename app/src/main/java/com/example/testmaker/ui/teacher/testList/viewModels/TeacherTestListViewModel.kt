package com.example.testmaker.ui.teacher.testList.viewModels

import androidx.lifecycle.ViewModel
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.ui.TestTeacherTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeacherTestListViewModel(

): ViewModel() {
    private val _teacherTest = MutableStateFlow<TeacherTest?>(null)
    val teacherTest = _teacherTest.asStateFlow()

    private val _teacherTestLoading = MutableStateFlow(false)
    val teacherTestLoading = _teacherTestLoading.asStateFlow()

    fun getTeacherTest(testId: String) {
        // TODO test data
        _teacherTest.tryEmit(TestTeacherTest.teacherTest)
    }
}