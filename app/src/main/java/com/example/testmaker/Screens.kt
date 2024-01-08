package com.example.testmaker

import com.example.testmaker.models.student.StudentTest
import com.example.testmaker.models.student.StudentTestStart
import com.example.testmaker.models.teacher.TeacherTest
import com.example.testmaker.models.teacher.TeacherTestQuestion
import com.example.testmaker.models.test.Test
import com.example.testmaker.models.users.Teacher
import com.example.testmaker.ui.admin.addTeacher.AdminAddTeacherFragment
import com.example.testmaker.ui.admin.main.AdminFragment
import com.example.testmaker.ui.auth.LoginFragment
import com.example.testmaker.ui.auth.registration.RegistrationFragment
import com.example.testmaker.ui.student.StudentFragment
import com.example.testmaker.ui.student.testInfo.StudentTestInfoFragment
import com.example.testmaker.ui.student.testPassResult.StudentTestPassResultFragment
import com.example.testmaker.ui.student.testQuestion.StudentTestQuestionFragment
import com.example.testmaker.ui.teacher.TeacherFragment
import com.example.testmaker.ui.teacher.configureTest.TeacherConfigureTestFragment
import com.example.testmaker.ui.teacher.results.testResults.TeacherTestResultsFragment
import com.example.testmaker.ui.teacher.testQuestion.TeacherTestQuestionFragment
import com.example.testmaker.ui.teacher.testQuestionList.TeacherTestQuestionListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object AuthScreens {
    fun loginScreen() = FragmentScreen { LoginFragment() }
    fun registrationScreen() = FragmentScreen { RegistrationFragment() }
}

object AdminScreens {
    fun adminScreen() = FragmentScreen { AdminFragment() }
    fun addTeacherScreen(teacher: Teacher? = null) = FragmentScreen {
        AdminAddTeacherFragment.getNewInstance(teacher)
    }
}

object TeacherScreens {
    fun teacherScreen() = FragmentScreen { TeacherFragment() }
    fun testResultsScreen(testId: String, testName: String) = FragmentScreen {
        TeacherTestResultsFragment.getNewInstance(testId, testName)
    }
    fun configureTestScreen(test: Test? = null) = FragmentScreen {
        TeacherConfigureTestFragment.getNewInstance(test)
    }
    fun testQuestionListScreen(testId: String, test: TeacherTest?) = FragmentScreen {
        TeacherTestQuestionListFragment.getNewInstance(testId, test)
    }
    fun addQuestionScreen(question: TeacherTestQuestion? = null) = FragmentScreen {
        TeacherTestQuestionFragment.getNewInstance(question)
    }
}

object StudentScreens {
    fun studentScreen() = FragmentScreen { StudentFragment() }
    fun testInfo(test: StudentTest) = FragmentScreen {
        StudentTestInfoFragment.getNewInstance(test)
    }
    fun testQuestionScreen(test: StudentTestStart) = FragmentScreen {
        StudentTestQuestionFragment.getNewInstance(test)
    }
    fun testPassResult() = FragmentScreen { StudentTestPassResultFragment() }
}