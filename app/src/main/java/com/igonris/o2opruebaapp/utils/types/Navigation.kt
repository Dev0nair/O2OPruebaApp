package com.igonris.o2opruebaapp.utils.types

import android.view.View

data class Navigation(
    val dest: Int,
    val args: List<Pair<String, Any>> = emptyList(),
    val sharedElements: List<View>? = null
)