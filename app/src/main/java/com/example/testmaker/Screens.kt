package com.example.testmaker

import com.example.testmaker.models.test.Test
import com.example.testmaker.models.users.Teacher
import com.example.testmaker.ui.admin.addTeacher.AdminAddTeacherFragment
import com.example.testmaker.ui.admin.main.AdminFragment
import com.example.testmaker.ui.auth.LoginFragment
import com.example.testmaker.ui.auth.registration.RegistrationFragment
import com.example.testmaker.ui.teacher.configureTest.TeacherConfigureTestFragment
import com.example.testmaker.ui.teacher.testList.TeacherTestListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object AuthScreens {
    fun loginScreen() = FragmentScreen { LoginFragment() }
    fun registrationScreen() = FragmentScreen { RegistrationFragment() }
}

object AdminScreens {
    fun adminScreen() = FragmentScreen { AdminFragment() }
    fun addTeacher(teacher: Teacher? = null) = FragmentScreen {
        AdminAddTeacherFragment.getNewInstance(teacher)
    }
}

object TeacherScreens {
    fun teacherScreen() = FragmentScreen { TeacherTestListFragment() }
    fun configureTest(test: Test? = null) = FragmentScreen {
        TeacherConfigureTestFragment.getNewInstance(test)
    }
}