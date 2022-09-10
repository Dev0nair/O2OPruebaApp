package com.igonris.o2opruebaapp.utils.extensions

import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController


fun Fragment.navigate(
    dest: Int,
    params: List<Pair<String, Any>>,
    sharedElements: List<View>? = null
) {
    var extras: FragmentNavigator.Extras? = null

    if (sharedElements != null) {

        // Esto que hago es para hacer funcionar la animación de transición
        // De esta forma, solo me encargo de poner el transitionName en el xml del elemento
        // de inicio y el de destino, siendo el de inicio <nombre>_small y el de destino <nombre>_big
        // Para aplicaciones más elaboradas con animaciones, se podría hacer un pequeño diccionario
        // y usar los que convenga para concatenar elementos con transiciones animadas
        extras = FragmentNavigatorExtras(
            *sharedElements
                .map { view ->
                    view.transitionName = view.transitionName.replace("_small", "_big")
                    view to view.transitionName
                }.toTypedArray()
        )
    }

    findNavController().navigate(dest, bundleOf(*params.toTypedArray()), null, extras)
}

fun Fragment.showError(error: String) {
    context?.let { ctx ->
        Toast.makeText(ctx, error, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.prepareForTransition(
    animation: Transition = TransitionInflater.from(requireContext()).inflateTransition(
        android.R.transition.move
    )
) {
    sharedElementEnterTransition = animation
    sharedElementReturnTransition = animation
}