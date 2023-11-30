package com.example.testmaker.core.utils.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

/**
 * Extension to prevent double navigation
 */
fun Fragment.navigate(
    directions: NavDirections,
    navOptions: NavOptions? = null,
    controller: NavController = findNavController()
) {
    safe(controller) {
        it.navigate(directions, navOptions)
    }
}

fun Fragment.navigateBack() {
    safe(findNavController()) {
        it.popBackStack()
    }
}

private fun Fragment.safe(controller: NavController, action: (NavController) -> Unit) {
    val currentDestination =
        (controller.currentDestination as? FragmentNavigator.Destination)?.className
            ?: (controller.currentDestination as? DialogFragmentNavigator.Destination)?.className
    if (currentDestination == this.javaClass.name) {
        action(controller)
    }
}

fun NavController.setGraph(
    @NavigationRes graphId: Int,
    @IdRes fragmentId: Int,
    args: Bundle? = null
) {
    val graph = navInflater.inflate(graphId)
    graph.setStartDestination(fragmentId)
    this.setGraph(
        graph,
        args
    )
}