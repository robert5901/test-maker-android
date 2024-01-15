package com.example.testmaker.ui.auth.registration

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.errors.ErrorManager
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.coroutine.observeOnStarted
import com.example.testmaker.databinding.FragmentRegistrationBinding
import com.example.testmaker.models.users.Student
import com.example.testmaker.ui.auth.registration.viewModels.RegisterViewModel
import org.koin.android.ext.android.inject

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val errorManager: ErrorManager by inject()
    private val viewModel: RegisterViewModel by inject()

    private var selectedGroupTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSpinner()
        configureViewModel()

        binding.registration.setOnClickListener {
            createStudent()
        }
    }

    private fun configureSpinner() {
        spinnerAdapter = ArrayAdapter(binding.groupSpinner.context, android.R.layout.simple_list_item_1)
        binding.groupSpinner.adapter = spinnerAdapter

        binding.groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = binding.groupSpinner.selectedItem
                if (selectedItem is String) {
                    selectedGroupTitle = selectedItem
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
    }

    private fun configureViewModel() {
        observeOnStarted(viewModel.loading) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.registration.isEnabled = !isLoading
            binding.email.isEnabled = !isLoading
            binding.name.isEnabled = !isLoading
            binding.password.isEnabled = !isLoading
        }

        observeOnStarted(viewModel.groups) { groups ->
            if (groups != null) {
                spinnerAdapter.clear()
                spinnerAdapter.addAll(groups.map { it.title })
                spinnerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun createStudent() {
        val isValid = validateEditTexts()
        if (!isValid) return

        val groups = viewModel.groups.value ?: return

        val selectedGroup = groups.find { it.title == selectedGroupTitle } ?: return

        val student = Student(
            selectedGroup.id,
            binding.email.text.toString(),
            binding.name.text.toString(),
            binding.password.text.toString()
        )

        viewModel.createStudent(student)
    }

    private fun validateEditTexts(): Boolean {
        var isAllFieldsValid = true

        if (binding.name.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.auth_register_student_name_error))
            isAllFieldsValid = false
        }
        if (binding.email.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.auth_email_error))
            isAllFieldsValid = false
        }
        if (binding.password.text.isBlank()) {
            errorManager.showError(ErrorManagerError.ResError(R.string.auth_password_error))
            isAllFieldsValid = false
        }
        if (binding.password.text.length < 6) {
            errorManager.showError(ErrorManagerError.ResError(R.string.auth_password_length_error))
            isAllFieldsValid = false
        }
        return isAllFieldsValid
    }
}