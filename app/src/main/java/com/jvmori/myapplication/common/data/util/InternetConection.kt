package com.jvmori.myapplication.common.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun isInternetConnection(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnected ?: false
}