package com.example.testmaker.ui.teacher.testQuestionList.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.testmaker.models.teacher.TeacherTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeacherTestQuestionListViewModel: ViewModel() {
    private val _test = MutableStateFlow<TeacherTest?>(null)
    val test = _test.asStateFlow()

    fun saveQuestionImage(uri: Uri) {
//        val teacherTest = _test.value?.question.
//        teacherTest
//        _test.tryEmit(teacherTest)
    }

    fun setTestId(testId: String) {
        val teacherTest = TeacherTest(testId, null, null)
        _test.tryEmit(teacherTest)
    }
}