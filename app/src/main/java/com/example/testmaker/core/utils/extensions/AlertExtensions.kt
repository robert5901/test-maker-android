package com.example.testmaker.core.utils.extensions

import android.content.Context
import com.example.testmaker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showAlertMessage(
    context: Context?,
    title: String?,
    message: String,
    actionTitle: String? = null,
    action: (() -> Unit)? = null) {
    if (context == null) return

    val builder = MaterialAlertDialogBuilder(context)
    builder.setTitle(title)
        .setMessage(message)
        .setCancelable(true)
        .setPositiveButton(actionTitle ?: context.resources?.getString(R.string.common_ok)) { dialog, _ ->
            dialog.dismiss()
            action?.invoke()
        }
    val alert = builder.create()
    alert.show()
}

fun showAlertMessageWithNegativeButton(
    context: Context?,
    title: String?,
    message: String,
    actionTitle: String? = null,
    negativeTitle: String? = null,
    action: (() -> Unit)? = null,
    declineAction: (() -> Unit)? = null,
    cancelAction: (() -> Unit)? = null
) {
    if (context == null) return

    val builder = MaterialAlertDialogBuilder(context)
    builder.setTitle(title)
        .setMessage(message)
        .setCancelable(true)
        .setPositiveButton(actionTitle ?: context.resources?.getString(R.string.common_ok)) { dialog, _ ->
            dialog.dismiss()
            action?.invoke()
        }
        .setNegativeButton(negativeTitle ?: context.resources?.getString(R.string.common_cancel)) { dialog, _ ->
            dialog.cancel()
            declineAction?.invoke()
        }
        .setOnCancelListener {
            cancelAction?.invoke()
        }
    val alert = builder.create()
    alert.show()
}