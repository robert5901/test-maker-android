package com.example.testmaker.ui.teacher.testQuestion.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.TeacherTestQuestionVariantViewBinding

class TeacherTestQuestionVariantView(context: Context, attrs: AttributeSet? = null): ConstraintLayout(context, attrs) {
    private val binding by viewBinding { TeacherTestQuestionVariantViewBinding.inflate(LayoutInflater.from(context), this) }

    private var variantNumber: Int? = null

    var onDeleteClicked: Action<Int>? = null
    var onRadioButtonSelected: Action<Int>? = null

    init {
        binding.delete.setOnClickListener {
            val variantNumber = variantNumber ?: return@setOnClickListener
            onDeleteClicked?.invoke(variantNumber)
        }

        binding.radioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val variantNumber = variantNumber ?: return@setOnCheckedChangeListener
                onRadioButtonSelected?.invoke(variantNumber)
            }
        }
    }

    fun setNumber(variantNumber: Int) {
        this.variantNumber = variantNumber
        binding.title.text = resources.getString(R.string.teacher_add_question_variant_view_title, variantNumber)
    }

    fun setAnswersType(isSingleAnswer: Boolean) {
        binding.radioButton.isVisible = isSingleAnswer
        binding.checkBox.isVisible = !isSingleAnswer
    }

    fun getText(): String {
        return binding.variant.text.toString()
    }

    fun getNumber(): Int {
        return this.variantNumber ?: return 0
    }

    fun checkRadioButton() {
        binding.radioButton.isChecked = true
    }

    fun checkCheckBox() {
        binding.checkBox.isChecked = true
    }

    fun uncheckRadioButton() {
        binding.radioButton.isChecked = false
    }

    fun getVariantNumberIfCheckBoxIsChecked(): Int? {
        if (binding.checkBox.isChecked) {
            return variantNumber
        }
        return null
    }

    fun setText(text: String) {
        binding.variant.setText(text)
    }
}