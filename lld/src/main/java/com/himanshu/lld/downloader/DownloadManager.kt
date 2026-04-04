package com.himanshu.lld.downloader

import downloader.DownloadListener
import downloader.DownloadRequest
import downloader.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

class DownloadManager(
    private val scope: CoroutineScope,
    private val listener: DownloadListener
) {

    private val downloader = Downloader(OkHttpClient())
    private val queue = DownloadQueue(scope, downloader, listener)
    fun download(
        request: DownloadRequest,
    ) {
        queue.add(request)
    }
    fun cancel(id: String) {
        queue.cancel(id)
    }
}

fun main() = runBlocking {

    val listener = object : DownloadListener {
        override fun onProgress(id: String, progress: Int) {
            println("Downloading... $progress%")
        }

        override fun onSuccess(id: String) {
            println("Download completed")
        }

        override fun onError(id: String, error: String) {
            println("Download failed: $error")
        }
    }

    val downloadManager = DownloadManager(this, listener)

    val request = DownloadRequest(
        id = "1",
        url = "https://nbg1-speed.hetzner.com/100MB.bin",
        status = Status.PENDING
    )


    downloadManager.download(request)

    delay(2000)
    downloadManager.cancel("1")
    delay(5000) // keep JVM alive
}