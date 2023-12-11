package com.example.testmaker.core.utils.extensions.coroutine

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withStateAtLeast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun Fragment.launchRepeatingWhen(
    state: Lifecycle.State,
    crossinline action: suspend CoroutineScope.() -> Unit
): Job = viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) {
        action()
    }
}

inline fun Fragment.launchWhenStateAtLeast(
    state: Lifecycle.State,
    crossinline action: suspend CoroutineScope.() -> Unit
): Job = viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.lifecycle.withStateAtLeast(state) {
        this.launch { action() }
    }
}

inline fun Fragment.launchWhenStarted(
    withRestart: Boolean = true,
    crossinline action: suspend CoroutineScope.() -> Unit
) =
    if (withRestart) launchRepeatingWhen(Lifecycle.State.STARTED, action) else launchWhenStateAtLeast(
        Lifecycle.State.STARTED, action)

inline fun Fragment.launchWhenResumed(
    withRestart: Boolean = true,
    crossinline action: suspend CoroutineScope.() -> Unit
) =
    if (withRestart) launchRepeatingWhen(Lifecycle.State.RESUMED, action) else launchWhenStateAtLeast(
        Lifecycle.State.RESUMED, action)

fun <T> Fragment.observeOnStarted(
    flow: Flow<T>,
    withRestart: Boolean = true,
    action: suspend (value: T) -> Unit
) {
    launchWhenStarted(withRestart) {
        flow.collect(action)
    }
}

fun <T> Fragment.observeOnResumed(
    flow: Flow<T>,
    withRestart: Boolean = true,
    action: suspend (value: T) -> Unit
) {
    launchWhenResumed(withRestart) {
        flow.collect(action)
    }
}