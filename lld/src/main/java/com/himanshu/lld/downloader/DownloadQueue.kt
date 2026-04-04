package com.himanshu.lld.downloader

import downloader.DownloadListener
import downloader.DownloadRequest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DownloadQueue(
    private val scope: CoroutineScope,
    private val downloader: Downloader,
    private val listener: DownloadListener
) {

    private val queue: ArrayDeque<DownloadRequest> = ArrayDeque()
    private var currentJob: Job? = null
    private var currentTask: DownloadRequest? = null

    fun add(task: DownloadRequest) {
        queue.add(task)
        processQueue()
    }

    fun cancel(id: String) {
        if (currentTask?.id == id) {
            currentJob?.cancel()
        } else {
            queue.removeAll { it.id == id }
        }
    }

    private fun processQueue() {
        if (currentJob?.isActive == true) return

        val task = queue.removeFirstOrNull() ?: return
        currentTask = task

        currentJob = scope.launch {
            try {
                downloader.download(task, listener)
            } catch (e: CancellationException) {
                // Task was cancelled
                println("Task ${task.id} cancelled")
            } catch (e: Exception) {
                listener.onError(task.id, e.message ?: "Unknown error")
            } finally {
                currentJob = null
                currentTask = null
                processQueue()
            }
        }
    }
}