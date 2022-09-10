package com.igonris.o2opruebaapp.repository.factory

import com.igonris.o2opruebaapp.repository.Configuration
import com.igonris.o2opruebaapp.repository.RepoBuildType
import com.igonris.o2opruebaapp.repository.factory.api.IPunkAPI
import com.igonris.o2opruebaapp.repository.factory.api.PunkAPIRepositoryImp
import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository

object PunkAPIRepositoryFactory {
    private const val API_V = "v2"
    const val BASE_URL = "https://api.punkapi.com/${API_V}/"

    fun getRepository(
        buildType: RepoBuildType = Configuration.PunkAPIRepoType,
        iPunkAPI: IPunkAPI
    ): IPunkAPIRepository =
        when (buildType) {
            RepoBuildType.REMOTE -> PunkAPIRepositoryImp(iPunkAPI)

            // Aquí podría ir un repo que haga uso de un sqlite, room o un json directamente para hacer pruebas o elegir en caso de que no haya internet o la red esté caída.
            RepoBuildType.LOCAL -> PunkAPIRepositoryImp(iPunkAPI)
        }
}