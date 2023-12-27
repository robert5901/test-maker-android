package com.example.testmaker.core.utils.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun Context.getColorCompat(@ColorRes id: Int) =
    ResourcesCompat.getColor(resources, id, theme)

fun Context.getDrawableCompat(@DrawableRes id: Int) =
    ResourcesCompat.getDrawable(resources, id, theme)

fun Context.getColorStateListCompat(@ColorRes id: Int) =
    ResourcesCompat.getColorStateList(resources, id, theme)