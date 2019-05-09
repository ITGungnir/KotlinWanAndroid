package app.itgungnir.kwa.common.redux

import android.app.Application
import com.google.gson.Gson
import my.itgungnir.rxmvvm.core.redux.BaseRedux

class AppRedux(context: Application) : BaseRedux<AppState>(
    context = context,
    initialState = AppState(),
    reducer = AppReducer(),
    middlewareList = listOf()
) {

    companion object {

        lateinit var instance: AppRedux

        fun init(context: Application) {
            instance = AppRedux(context)
        }
    }

    override fun deserializeToCurrState(json: String): AppState? =
        Gson().fromJson(json, AppState::class.java)

    // 判断用户是否已登录
    fun isUserIn() = !currState().userName.isNullOrBlank()

    // 判断用户是否已收藏了某篇文章
    fun isCollected(articleId: Int) = currState().collectIds.contains(articleId)
}