package app.itgungnir.kwa.common.widget.recycler_list

data class RecyclerListStatus<T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {

    enum class Status {
        PROCESSING,
        SUCCESS,
        COMPLETE,
        ERROR
    }

    companion object {

        fun <T> processing() =
            RecyclerListStatus<T>(status = Status.PROCESSING)

        fun <T> success(data: T) =
            RecyclerListStatus(status = Status.SUCCESS, data = data)

        fun <T> complete() =
            RecyclerListStatus<T>(status = Status.COMPLETE)

        fun <T> error(error: Throwable) =
            RecyclerListStatus<T>(status = Status.ERROR, error = error)
    }
}