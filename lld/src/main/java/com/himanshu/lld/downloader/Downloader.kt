package com.himanshu.lld.downloader

import downloader.DownloadListener
import downloader.DownloadRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.cancellation.CancellationException

class Downloader(
    private val client: OkHttpClient
) {
    suspend fun download(
        task: DownloadRequest,
        listener: DownloadListener
    ) = withContext(Dispatchers.IO) {

        val request = Request.Builder()
            .url(task.url)
            .addHeader("User-Agent", "Mozilla/5.0")
            .build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw Exception("Download failed")
        }

        val body = response.body ?: throw Exception("Empty body")

        val totalBytes = body.contentLength()
        val inputStream = body.byteStream()

        val file = File("path_to_save/${task.id}.file")

        // Create parent directory if not exists
        file.parentFile?.let {
            if (!it.exists()) {
                it.mkdirs()
            }
        }

        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(8 * 1024)
        var bytesRead: Int
        var downloadedBytes = 0L

        while (inputStream.read(buffer).also { bytesRead = it } != -1) {

            // 👇 Handle cancellation
            if (!coroutineContext.isActive) {
                inputStream.close()
                outputStream.close()
                file.delete() // cleanup
                throw CancellationException()
            }

            outputStream.write(buffer, 0, bytesRead)
            downloadedBytes += bytesRead

            val progress = ((downloadedBytes * 100) / totalBytes).toInt()
            listener.onProgress(task.id, progress)
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()

        listener.onSuccess(task.id)
    }
}