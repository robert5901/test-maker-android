package com.example.testmaker.ui.teacher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentTeacherBinding
import com.example.testmaker.ui.teacher.results.testList.TeacherResultsTestListFragment
import com.example.testmaker.ui.teacher.testList.TeacherTestListFragment
import com.google.android.material.navigation.NavigationBarView

class TeacherFragment : Fragment(R.layout.fragment_teacher) {
    private val binding by viewBinding(FragmentTeacherBinding::bind)

    private val testListFragment by lazy { TeacherTestListFragment() }
    private val resultsFragment by lazy { TeacherResultsTestListFragment() }

    private val navListener = NavigationBarView.OnItemSelectedListener { item ->
        val activeFragment = getBottomNavViewFragment(binding.bottomNavView.selectedItemId) ?: return@OnItemSelectedListener false
        val selectedFragment = getBottomNavViewFragment(item.itemId) ?: return@OnItemSelectedListener false

        if (binding.bottomNavView.selectedItemId != item.itemId) {
            val fragmentTransaction = childFragmentManager.beginTransaction()

            fragmentTransaction.remove(activeFragment)
            fragmentTransaction.replace(R.id.teacher_container, selectedFragment, selectedFragment.tag)

            fragmentTransaction.commit()
        }

        true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().replace(R.id.teacher_container, testListFragment, testListFragment.tag).commit()
            binding.bottomNavView.post {
                binding.bottomNavView.menu.findItem(R.id.testListFragment)?.isChecked = true
            }
        }

        binding.bottomNavView.setOnItemSelectedListener(navListener)
        binding.bottomNavView.itemIconTintList = null
        if (binding.bottomNavView.selectedItemId != R.id.testListFragment && binding.bottomNavView.selectedItemId != R.id.resultsFragment) {
            childFragmentManager.beginTransaction().add(R.id.teacher_container, testListFragment, testListFragment.tag).commit()
        }
    }

    private fun getBottomNavViewFragment(itemId: Int): Fragment? = when (itemId) {
        R.id.testListFragment -> {
            testListFragment
        }

        R.id.resultsFragment -> {
            resultsFragment
        }

        else -> {
            null
        }
    }
}