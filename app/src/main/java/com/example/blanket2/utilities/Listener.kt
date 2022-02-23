package com.example.blanket2.utilities

/**
 * A general interface for a listener
 */
fun interface Listener<T>{
    fun receive(element: T?)
}