package com.example.aimovies.data.remote

import com.example.aimovies.data.Constants.GOOGLE_PORT
import com.example.aimovies.data.Constants.GoogleDNS
import com.example.aimovies.data.Constants.INTERNET_CONNECTION_TIME_OUT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

/**
 * Created by A.Elkhami on 24/07/2023.
 */
suspend fun isOnline(): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val timeoutMs = INTERNET_CONNECTION_TIME_OUT
            val socket = Socket()
            val socketAddress: SocketAddress = InetSocketAddress(GoogleDNS, GOOGLE_PORT)
            socket.connect(socketAddress, timeoutMs)
            socket.close()
            true
        } catch (e: IOException) {
            false
        }
    }
}