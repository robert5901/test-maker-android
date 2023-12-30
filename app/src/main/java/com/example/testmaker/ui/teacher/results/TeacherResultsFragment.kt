package com.example.testmaker.ui.teacher.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentTeacherResutlsBinding

class TeacherResultsFragment : Fragment(R.layout.fragment_teacher_resutls) {
    private val binding by viewBinding(FragmentTeacherResutlsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}