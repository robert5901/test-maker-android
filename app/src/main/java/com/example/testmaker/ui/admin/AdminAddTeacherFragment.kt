package com.example.testmaker.ui.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentAdminAddTeacherBinding
import com.example.testmaker.models.admin.Teacher
import java.security.SecureRandom

class AdminAddTeacherFragment: Fragment(R.layout.fragment_admin_add_teacher) {
    private val binding by viewBinding(FragmentAdminAddTeacherBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teacher: Teacher? = arguments?.getParcelable(EXTRA_TEACHER)

        binding.generatePassword.setOnClickListener {
            binding.password.setText(generatePassword())
        }

        if (teacher != null){
            setData(teacher)
        }
    }

    private fun setData(teacher: Teacher) {
        binding.login.setText(teacher.login)
        binding.name.setText(teacher.name)
    }

    private fun generatePassword(): String {
        val charset = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val secureRandom = SecureRandom()
        return (1..8)
            .map { charset[secureRandom.nextInt(charset.size)] }
            .joinToString("")
    }

    companion object {
        private const val EXTRA_TEACHER = "extra_teacher"

        fun getNewInstance(teacher: Teacher?): AdminAddTeacherFragment {
            if (teacher == null) return AdminAddTeacherFragment()

            return AdminAddTeacherFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_TEACHER, teacher)
                }
            }
        }
    }
}