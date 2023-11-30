package com.example.testmaker.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.AdminScreens
import com.example.testmaker.AuthScreens
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentLoginBinding
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registration.setOnClickListener {
            router.navigateTo(AuthScreens.registrationScreen())
        }

        binding.login.setOnClickListener {
            router.replaceScreen(AdminScreens.adminScreen())
        }
    }
}