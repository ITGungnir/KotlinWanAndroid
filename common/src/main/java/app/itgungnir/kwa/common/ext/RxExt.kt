package app.itgungnir.kwa.common.ext

import app.itgungnir.kwa.common.http.Result
import app.itgungnir.kwa.common.http.exception.AuthException
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<Result<T>>.handleResult() = compose { upsteam ->
    upsteam.flatMap {
        when (it.errorCode) {
            0 ->
                Single.just(it.data)
            -1001 ->
                Single.error(AuthException())
            else ->
                Single.error(Throwable(it.errorMsg))
        }
    }
}!!

fun <T> Single<T>.io2Main(): Single<T> {
    val transformer: SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    return compose(transformer)
}