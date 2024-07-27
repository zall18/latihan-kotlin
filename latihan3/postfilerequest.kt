package com.example.latihan1

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files

@RequiresApi(Build.VERSION_CODES.O)
suspend fun postfileRequest(connectionString: String, file: File?, jsonObject: JSONObject, token: String?): Result<String> {
    return withContext(Dispatchers.IO){
        try {
            var url = URL(connectionString)
            var redirect = false
            var response = StringBuilder()
            val boundary = "Boundary-" + System.currentTimeMillis()

            do {
                var connection = url.openConnection() as HttpURLConnection
                connection.instanceFollowRedirects = true
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
                connection.setRequestProperty("Accept", "application/json")
                if (token != null) {
                    connection.setRequestProperty("Authorization", "Bearer $token")
                }
                connection.doOutput = true

                val outputStream = connection.outputStream
                outputStream.use { output ->
                    writePart(output, boundary, "json", "application/json", jsonObject.toString().toByteArray())
                    if (file != null) {
                        writePart(output, boundary, "foto", Files.probeContentType(file.toPath()), file.readBytes())
                    }
                    output.write("--$boundary--\r\n".toByteArray())
                }


                var responseCode = connection.responseCode

                if(responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP){
                    var newUrl = connection.getHeaderField("Location")
                    url = URL(newUrl)
                    redirect = true

                }else{
                    redirect = false

                    var inputStream = if(responseCode in 200 .. 299){
                        connection.inputStream
                    }else{
                        connection.errorStream
                    }

                    var line: String?
                    var reader = BufferedReader(InputStreamReader(inputStream))
                    while (reader.readLine().also { line = it } != null){
                        response.append(line)
                    }

                    if(responseCode !in 200 .. 299){
                        return@withContext Result.failure(Exception("Error with response code $responseCode \n $response"))
                    }
                }
                connection.disconnect()
            }while (redirect)
            Result.success(response.toString())
        }catch (e: Exception){
            Result.failure(e)

        }
    }

}
fun writePart(output: OutputStream, boundary: String, name: String, contentType: String, content: ByteArray) {
    output.write("--$boundary\r\n".toByteArray())
    output.write("Content-Disposition: form-data; name=\"$name\"\r\n".toByteArray())
    output.write("Content-Type: $contentType\r\n\r\n".toByteArray())
    output.write(content)
    output.write("\r\n".toByteArray())
}