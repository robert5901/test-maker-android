package com.example.testmaker.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var adapter: ArrayAdapter<String>
    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        configureSpinner()
    }

    private fun configureSpinner() {
        adapter = ArrayAdapter(binding.groupSpinner.context, android.R.layout.simple_list_item_1, getSpinnerList())
        binding.groupSpinner.adapter = adapter

        binding.groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = binding.groupSpinner.selectedItem
                if (selectedItem is String) {
                    val selectedGroup = selectedItem
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    // TODO test data заменить на получение списка групп с api в observe
    private fun getSpinnerList(): List<String> {
        return listOf("4480","4481","4482","4483","4484","4480","4481","4482","4483","4484",)
    }
}