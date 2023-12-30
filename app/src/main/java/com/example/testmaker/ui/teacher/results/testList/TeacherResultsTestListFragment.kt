package com.example.testmaker.ui.teacher.results.testList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.TeacherScreens
import com.example.testmaker.databinding.FragmentTeacherResutlsTestListBinding
import com.example.testmaker.ui.TestListData
import com.example.testmaker.ui.teacher.results.testList.adapters.TeacherResultsTestListAdapter
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class TeacherResultsTestListFragment : Fragment(R.layout.fragment_teacher_resutls_test_list) {
    private lateinit var adapter: TeacherResultsTestListAdapter

    private val binding by viewBinding(FragmentTeacherResutlsTestListBinding::bind)
    private val router: Router by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureAdapter()

        // TODO test data
        adapter.set(TestListData.tests)
    }

    private fun configureAdapter() {
        adapter = TeacherResultsTestListAdapter()

        adapter.onTestSelected = { test ->
            router.navigateTo(TeacherScreens.testResults(test.id, test.name))
        }

        binding.recyclerView.adapter = adapter
    }
}