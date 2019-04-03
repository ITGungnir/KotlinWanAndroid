package my.itgungnir.kotlin.wan.android.splash

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import my.itgungnir.rxmvvm.core.mvvm.BaseVM
import java.util.concurrent.TimeUnit

class SplashVM : BaseVM() {

    val countDownState = MutableLiveData<Unit>()

    /**
     * 倒计时
     */
    @SuppressLint("CheckResult")
    fun startCountDown() {
        Observable.timer(2L, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                this.countDownState.value = Unit
            }
    }
}