package app.itgungnir.kwa.support.setting

import android.annotation.SuppressLint
import android.content.Context
import app.itgungnir.kwa.common.R
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.util.CacheUtil
import io.reactivex.Single
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class SettingViewModel : BaseViewModel<SettingState>(initialState = SettingState()) {

    @SuppressLint("CheckResult")
    fun getSettingList(context: Context) {
        Single.just(
            listOf(
                SettingState.DividerVO,
                SettingState.CheckableVO(
                    1,
                    context.getString(R.string.icon_auto_cache),
                    "自动缓存",
                    AppRedux.instance.isAutoCache()
                ),
                SettingState.CheckableVO(
                    2,
                    context.getString(R.string.icon_no_image),
                    "无图模式",
                    AppRedux.instance.isNoImage()
                ),
                SettingState.CheckableVO(
                    3,
                    context.getString(R.string.icon_dark_mode),
                    "夜间模式",
                    AppRedux.instance.isDarkMode()
                ),
                SettingState.DividerVO,
                SettingState.DigitalVO(
                    4,
                    context.getString(R.string.icon_clear_cache),
                    "清除缓存",
                    CacheUtil.instance.getCacheSize()
                ),
                SettingState.DividerVO,
                SettingState.NavigableVO(5, context.getString(R.string.icon_feedback), "意见反馈"),
                SettingState.NavigableVO(6, context.getString(R.string.icon_about_us), "关于我们")
            )
        ).subscribe({
            if (AppRedux.instance.isUserIn()) {
                setState {
                    copy(
                        items = it + listOf(SettingState.DividerVO, SettingState.ButtonVO),
                        error = null
                    )
                }
            } else {
                setState {
                    copy(
                        items = it,
                        error = null
                    )
                }
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