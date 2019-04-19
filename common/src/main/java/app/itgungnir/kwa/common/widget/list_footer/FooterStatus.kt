package app.itgungnir.kwa.common.widget.list_footer

data class FooterStatus(val status: Status) {

    enum class Status(val title: String) {
        PROGRESSING("正在加载..."),
        NO_MORE("没有更多数据了"),
        SUCCEED("正在加载..."),
        FAILED("加载失败，请重试")
    }
}