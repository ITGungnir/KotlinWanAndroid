package app.itgungnir.kwa.common.widget.recycler_footer

data class FooterStatus(val status: Status) {

    enum class Status(val title: String) {
        PROGRESSING("正在加载..."),
        NO_MORE("没有更多数据了"),
        SUCCEED("正在加载..."),
        FAILED("加载失败，请重试")
    }

    companion object {

        fun loading() =
            FooterStatus(status = Status.PROGRESSING)

        fun succeed(hasMore: Boolean) = when (hasMore) {
            true -> FooterStatus(Status.SUCCEED)
            else -> FooterStatus(Status.NO_MORE)
        }

        fun failed() =
            FooterStatus(status = Status.FAILED)
    }
}