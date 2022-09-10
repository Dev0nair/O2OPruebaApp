package com.igonris.o2opruebaapp.utils

import com.igonris.o2opruebaapp.utils.types.ErrorType
import com.igonris.o2opruebaapp.utils.types.ResultType
import retrofit2.Response

object Utils {

    suspend fun <T> responseToResultType(petition: suspend () -> Response<T>): ResultType<T> {
        return try {

            val response = petition()

            if(response.isSuccessful && response.body() != null) {
                 ResultType.Success(response.body()!!)
            } else if(response.errorBody() != null){
                ResultType.Error(ErrorType.APIError("API Error"))
            } else {
                throw Exception("Unknown error found")
            }

        } catch (e: Exception) {
            ResultType.Error(ErrorType.Other(desc = e.message.toString()))
        }
    }

}