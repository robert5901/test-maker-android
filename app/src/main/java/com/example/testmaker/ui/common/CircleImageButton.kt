package com.example.testmaker.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.testmaker.R

class CircleImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val image: ImageView

    init {
        inflate(context, R.layout.view_circle_image_button, this)
        image = findViewById(R.id.circle_image_button_image)
        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.CircleImageButton, defStyleAttr, 0)
        val imageDrawable =
            attributes.getDrawable(R.styleable.CircleImageButton_circle_image_button_image)
        attributes.recycle()

        image.setImageDrawable(imageDrawable)
        image.setOnClickListener {
            this.performClick()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cardView = findViewById<CardView>(R.id.circle_image_card_view)
        cardView.radius = if (w == h)
            (w / 2).toFloat()
        else
            resources.getDimension(R.dimen.circle_image_default_radius)
    }

    override fun setEnabled(enabled: Boolean) {
        image.isEnabled = enabled
        image.alpha = if (enabled) 1F else 0.5F
        super.setEnabled(enabled)
    }
}