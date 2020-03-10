package com.jvmori.myapplication.common.data

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class Resource<T>(
    val status: Status?,
    val data: T?,
    val message: String?
) {

    sealed class Status{
        object SUCCESS : Status()
        object ERROR : Status()
        object LOADING : Status()
        object NETWORK_ERROR : Status()
    }

    companion object {
        fun <T> success(@Nullable data: T): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(@NonNull msg: String, @Nullable data: T?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(@Nullable data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> networkError(data : T?, message: String?) : Resource<T>{
            return Resource(
                Status.NETWORK_ERROR,
                data,
                message
            )
        }
    }
}