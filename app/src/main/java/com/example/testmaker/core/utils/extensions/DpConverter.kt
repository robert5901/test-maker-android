package com.example.testmaker.core.utils.extensions

import android.content.res.Resources
import android.util.TypedValue

/**
 * Converts DP to Pixels. 1.dp = 1dp = Npx
 */
val Number.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )