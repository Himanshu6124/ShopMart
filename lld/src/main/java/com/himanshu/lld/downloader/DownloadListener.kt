package downloader

interface DownloadListener {
    fun onProgress(id: String, progress: Int)
    fun onSuccess(id: String)
    fun onError(id: String, error: String)
}
