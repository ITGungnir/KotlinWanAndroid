package app.itgungnir.kwa.weixin

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.http.HttpClient
import app.itgungnir.kwa.common.http.handleResult
import app.itgungnir.kwa.common.http.io2Main
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class WeixinViewModel : BaseViewModel<WeixinState>(initialState = WeixinState()) {

    /**
     * 获取公众号分类
     */
    @SuppressLint("CheckResult")
    fun getWeixinTabs() {
        HttpClient.api.weixinTabs()
            .handleResult()
            .io2Main()
            .map { it.map { item -> WeixinState.WeixinTabVO(item.id, item.name) } }
            .subscribe({
                setState {
                    copy(
                        tabs = it,
                        currTab = it[0],
                        error = null
                    )
                }
            }, {
                setState {
                    copy(
                        error = it
                    )
                }
            })
    }
}