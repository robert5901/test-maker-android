package com.example.testmaker.ui.core.views

import android.animation.ObjectAnimator
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testmaker.core.ActionUnit
import com.example.testmaker.databinding.ViewNotificationBinding

class NotificationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by viewBinding {
        ViewNotificationBinding.inflate(LayoutInflater.from(context), this)
    }

    private val timer: CountDownTimer by lazy {
        object : CountDownTimer(visibleDuration, visibleDuration) {
            override fun onTick(millisUntilFinished: Long) { }

            override fun onFinish() {
                hide()
                onClose?.invoke()
            }
        }
    }

    private var onClose: ActionUnit? = null

    init {
        binding.closeButton.setOnClickListener {
            timer.cancel()
            hide()
            onClose?.invoke()
        }
        setBackgroundColor(ResourcesCompat.getColor(resources, android.R.color.transparent, context.theme))
    }

    fun setOnCloseClickListener(onClose: () -> Unit) {
        this.onClose = onClose
    }

    fun setData(text: String) {
        binding.message.text = text
    }

    fun show(animated: Boolean = true) {
        isVisible = true

        ObjectAnimator.ofFloat(this, "translationY", startPosition, 0f).apply {
            duration = if (animated) animationDuration else 0L
            interpolator = DecelerateInterpolator()
            start()
        }

        timer.start()
    }

    fun hide(animated: Boolean = true) {
        ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
            duration = if (animated) animationDuration else 0L
            interpolator = AccelerateInterpolator()
            start()
        }.doOnEnd {
            isVisible = false
            alpha = 1f
        }
    }

    companion object {
        private const val startPosition = -500f
        private const val animationDuration = 200L
        private const val visibleDuration = 5000L
    }
}