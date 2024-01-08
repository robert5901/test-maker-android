package com.example.testmaker.ui.student.testPassResult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentTestPassResultBinding

class StudentTestPassResultFragment: Fragment(R.layout.fragment_test_pass_result) {
    val binding by viewBinding(FragmentTestPassResultBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}