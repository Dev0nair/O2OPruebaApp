package com.igonris.o2opruebaapp.utils.types

sealed class ErrorType(val desc: String) {

    class APIError(desc: String) : ErrorType(desc)
    class Other(desc: String) : ErrorType(desc)
}
