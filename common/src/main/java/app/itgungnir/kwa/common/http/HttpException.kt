package app.itgungnir.kwa.common.http

class HttpException(errorMsg: String) : Throwable(message = errorMsg)