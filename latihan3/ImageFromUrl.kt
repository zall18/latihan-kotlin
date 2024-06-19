package com.example.latihan3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

suspend fun LoadImageFromURL(urlstring: String): Bitmap?{
    return withContext(Dispatchers.IO){
        try {
            val url = URL(urlstring)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream: InputStream =connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}