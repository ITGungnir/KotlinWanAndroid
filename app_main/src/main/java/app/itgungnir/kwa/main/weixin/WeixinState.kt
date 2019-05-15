package app.itgungnir.kwa.main.weixin

import my.itgungnir.rxmvvm.core.mvvm.State

data class WeixinState(
    val tabs: List<WeixinTabVO> = listOf(),
    val currTab: WeixinTabVO? = null,
    val k: String = "",
    val error: Throwable? = null
) : State {

    data class WeixinTabVO(
        val id: Int,
        val name: String
    )
}