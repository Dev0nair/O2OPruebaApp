package com.igonris.o2opruebaapp.utils.types

/**
 * Esta clase sirve para crear elementos de los LiveData, para que el ciclo de vida no repita iteracciones al escuchar.
 * Esto puede pasar al girar la pantalla, por ejemplo, siendo el ultimo valor del liveData como valor en la recamara.
 * */
class ManagedItem<T>(val item: T) {

    private var handled = false

    private fun isHandled() = handled

    fun doIfUnhandled(action: (T) -> Unit) {
        if (!isHandled()) {
            handled = true
            action(item)
        }
    }
}