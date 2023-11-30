package com.example.testmaker.ui.common

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.R
import com.example.testmaker.core.utils.extensions.dp
import com.example.testmaker.core.utils.extensions.getColorCompat
import com.example.testmaker.databinding.LoadingButtonBinding
import com.google.android.material.card.MaterialCardView

// TODO delete unused code
class LoadingButton
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding by viewBinding {
        LoadingButtonBinding.inflate(LayoutInflater.from(context), this)
    }

    private lateinit var type: Type

    init {
        radius = 12.dp
        elevation = 0f

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton)
        binding.textView.text = attributes.getString(R.styleable.LoadingButton_loading_button_text)

        val defaultButtonTextSize = 18.dp.toInt()
        val buttonTextSize = attributes.getDimensionPixelSize(
            R.styleable.LoadingButton_loading_button_text_size,
            defaultButtonTextSize
        )
        binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize.toFloat())

        val typeInt = attributes.getInt(
            R.styleable.LoadingButton_loading_button_type,
            Type.GREEN.rawValue
        )
        setType(Type.fromInt(typeInt))

        if (attributes.hasValue(R.styleable.LoadingButton_loading_button_progressBarTint)) {
            val progressBarTint = attributes.getColor(
                R.styleable.LoadingButton_loading_button_progressBarTint,
                ContextCompat.getColor(context, R.color.white)
            )
            binding.loading.indeterminateTintList = ColorStateList.valueOf(progressBarTint)
        }

        attributes.recycle()
    }

    fun startLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.textView.visibility = View.INVISIBLE
        this.isClickable = false
        this.isFocusable = false
    }

    fun stopLoading() {
        binding.loading.visibility = View.GONE
        binding.textView.visibility = View.VISIBLE
        this.isClickable = true
        this.isFocusable = true
    }

    fun setButtonText(text: String) {
        binding.textView.text = text
    }

    fun setType(type: Type) {
        this.type = type
        when (type) {
            Type.GREEN -> {
                setCardBackgroundColor(context.getColorCompat(R.color.green))
                setRippleColorResource(R.color.white)
                binding.textView.setTextColor(context.getColorCompat(R.color.white))
            }

            Type.GREY -> {
                setCardBackgroundColor(context.getColorCompat(R.color.btn_grey))
                setRippleColorResource(R.color.grey_4)
                binding.textView.setTextColor(context.getColorCompat(R.color.black))
            }

            Type.RED -> {
                setCardBackgroundColor(context.getColorCompat(R.color.red))
                setRippleColorResource(R.color.white)
                binding.textView.setTextColor(context.getColorCompat(R.color.white))
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        if (enabled) {
            alpha = 1.0f
            setType(type)
        } else {
            alpha = 0.5f
            setType(Type.GREY)
        }
    }

    enum class Type(val rawValue: Int) {
        GREEN(0),
        GREY(1),
        RED(2);

        companion object {
            fun fromInt(value: Int) = values().find { it.rawValue == value } ?: GREEN
        }
    }
}