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
}