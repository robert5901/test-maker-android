package com.example.testmaker.ui.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentAdminBinding
import com.example.testmaker.ui.admin.groups.AdminGroupListFragment
import com.example.testmaker.ui.admin.teacherList.AdminTeacherListFragment
import com.google.android.material.navigation.NavigationBarView

class AdminFragment: Fragment(R.layout.fragment_admin) {
    private val binding by viewBinding(FragmentAdminBinding::bind)

    private val teacherListFragment by lazy { AdminTeacherListFragment() }
    private val groupListFragment by lazy { AdminGroupListFragment() }

    private val navListener = NavigationBarView.OnItemSelectedListener { item ->
        val activeFragment = getBottomNavViewFragment(binding.bottomNavView.selectedItemId) ?: return@OnItemSelectedListener false
        val selectedFragment = getBottomNavViewFragment(item.itemId) ?: return@OnItemSelectedListener false

        if (binding.bottomNavView.selectedItemId != item.itemId) {
            val fragmentTransaction = childFragmentManager.beginTransaction()

            fragmentTransaction.remove(activeFragment)
            fragmentTransaction.replace(R.id.admin_container, selectedFragment, selectedFragment.tag)

            fragmentTransaction.commit()
        }

        true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().replace(R.id.admin_container, teacherListFragment, teacherListFragment.tag).commit()
            binding.bottomNavView.post {
                binding.bottomNavView.menu.findItem(R.id.teacherListFragment)?.isChecked = true
            }
        }

        binding.bottomNavView.setOnItemSelectedListener(navListener)
        binding.bottomNavView.itemIconTintList = null
        if (binding.bottomNavView.selectedItemId != R.id.teacherListFragment && binding.bottomNavView.selectedItemId != R.id.groupListFragment) {
            childFragmentManager.beginTransaction().add(R.id.admin_container, teacherListFragment, teacherListFragment.tag).commit()
        }
    }

    private fun getBottomNavViewFragment(itemId: Int): Fragment? = when (itemId) {
        R.id.teacherListFragment -> {
            teacherListFragment
        }

        R.id.groupListFragment -> {
            groupListFragment
        }

        else -> {
            null
        }
    }
}