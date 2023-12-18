package com.example.testmaker.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.testmaker.core.errors.ErrorManagerError
import com.example.testmaker.core.utils.extensions.dp
import com.example.testmaker.ui.core.views.NotificationView

class NotificationsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        orientation = VERTICAL
    }

    fun showNotification(error: ErrorManagerError) {
        val errorMessage = error.getLocalizedString(context) ?: return

        val notificationView = NotificationView(context).apply {
            layoutParams = MarginLayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 8.dp.toInt()) }
        }
        notificationView.setData(errorMessage)
        notificationView.show()
        notificationView.setOnCloseClickListener {
            removeView(notificationView)
        }
        addView(notificationView)
    }
}