package com.igonris.o2opruebaapp.utils.types

sealed class ResultType<T> {

    class Success<T>(val data: T) : ResultType<T>()
    class Error<T>(val error: ErrorType) : ResultType<T>()

}
