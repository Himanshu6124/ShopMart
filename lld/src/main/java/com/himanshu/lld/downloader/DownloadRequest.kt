package downloader

import kotlinx.coroutines.Job

data class DownloadRequest(
    val id: String,
    val url: String,
    var status: Status = Status.PENDING,
    var job: Job? = null
)

enum class Status {
    PENDING, DOWNLOADING, COMPLETED, FAILED
}