package com.example.testmaker.core.utils.extensions.coroutine

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun AppCompatActivity.launchWhen(
    state: Lifecycle.State,
    crossinline action: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(state) {
        action()
    }
}

inline fun AppCompatActivity.launchWhenCreated(crossinline action: suspend CoroutineScope.() -> Unit) =
    launchWhen(Lifecycle.State.CREATED, action)

inline fun AppCompatActivity.launchWhenStarted(crossinline action: suspend CoroutineScope.() -> Unit) =
    launchWhen(Lifecycle.State.STARTED, action)

inline fun AppCompatActivity.launchWhenResumed(crossinline action: suspend CoroutineScope.() -> Unit) =
    launchWhen(Lifecycle.State.RESUMED, action)

fun <T> AppCompatActivity.observeOnCreated(
    flow: Flow<T>,
    action: suspend (value: T) -> Unit
) {
    launchWhenCreated {
        flow.collect(action)
    }
}

fun <T> AppCompatActivity.observeOnStarted(
    flow: Flow<T>,
    action: suspend (value: T) -> Unit
) {
    launchWhenStarted {
        flow.collect(action)
    }
}

fun <T> AppCompatActivity.observeOnResumed(
    flow: Flow<T>,
    action: suspend (value: T) -> Unit
) {
    launchWhenResumed {
        flow.collect(action)
    }
}