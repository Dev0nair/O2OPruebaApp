package com.igonris.o2opruebaapp.repository

enum class RepoBuildType { LOCAL, REMOTE }

object Configuration {

    // Usado para elegir el tipo de repositorio, es decir, una API, una base de datos local, una api distinta tipo dev/pre/pro...
    val PunkAPIRepoType = RepoBuildType.REMOTE
}