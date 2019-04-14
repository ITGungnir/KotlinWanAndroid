package app.itgungnir.kwa.common.http

class HttpException(val errorCode: Int, errorMsg: String) :
    Throwable(message = errorMsg)