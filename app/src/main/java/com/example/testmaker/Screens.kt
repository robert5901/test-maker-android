package com.example.testmaker

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

}