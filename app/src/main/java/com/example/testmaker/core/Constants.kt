package com.example.testmaker.core

typealias ActionUnit = (() -> Unit)
typealias Action<T> = ((T) -> Unit)