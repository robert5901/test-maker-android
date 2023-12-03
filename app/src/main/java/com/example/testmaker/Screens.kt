package com.example.testmaker

import com.example.testmaker.models.admin.Teacher
import com.example.testmaker.ui.admin.AdminAddTeacherFragment
import com.example.testmaker.ui.admin.AdminFragment
import com.example.testmaker.ui.auth.LoginFragment
import com.example.testmaker.ui.auth.RegistrationFragment
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