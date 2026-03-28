package com.himanshu.lld.downloader

import downloader.DownloadListener
import downloader.DownloadRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DownloadQueue(
    private val scope: CoroutineScope,
    private val downloader: Downloader,
    private val listener: DownloadListener
) {

    private val queue: ArrayDeque<DownloadRequest> = ArrayDeque()
    private var isDownloading = false

    fun add(task: DownloadRequest) {
        queue.add(task)
        processQueue()
    }

    private fun processQueue() {
        if (isDownloading) return

        val task = queue.removeFirstOrNull() ?: return
        isDownloading = true

        scope.launch {
            downloader.download(task, listener)
            isDownloading = false
            processQueue() // next task
        }
    }
}