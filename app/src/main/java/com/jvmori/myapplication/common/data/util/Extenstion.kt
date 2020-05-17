package com.jvmori.myapplication.common.data.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.paging.DataSource

fun <T> getFactory(dataSource: DataSource<Int, T>): DataSource.Factory<Int, T> {
    return object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            return dataSource
        }
    }
}

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) {
    val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(resId, args, navOptions, navExtras)
    }
}