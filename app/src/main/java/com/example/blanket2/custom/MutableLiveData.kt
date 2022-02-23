package com.example.blanket2.custom

import android.content.Context
import com.example.blanket2.utilities.Listener

class MutableLiveData<T>(initial: T?){
    var observers: MutableList<Listener<T>> = mutableListOf()
    var element: T? = initial
        set(value) {
            field = value
            observers.forEach{
                it.receive(field)
            }
        }
    fun observe(context: Context, listener: Listener<T>){
        observers.add(listener)
    }
}