package com.example.testmaker.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton

class HighlightedButton(context: Context, attrs: AttributeSet? = null): AppCompatButton(context, attrs) {
    init {
        setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                    this.alpha = 0.3F
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                    this.alpha = 1.0F
                }
                MotionEvent.ACTION_MOVE -> {
                    if (motionEvent.y < 0F || motionEvent.y > view.y + view.height) {
                        this.alpha = 1.0F
                    }
                }
            }

            view.onTouchEvent(motionEvent)
        }
    }
}