package app.itgungnir.kwa.common.http

data class Result<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)