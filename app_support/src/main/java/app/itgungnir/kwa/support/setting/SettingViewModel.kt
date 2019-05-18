package app.itgungnir.kwa.support.setting

import android.annotation.SuppressLint
import app.itgungnir.kwa.common.*
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.util.CacheUtil
import io.reactivex.Single
import my.itgungnir.rxmvvm.core.mvvm.BaseViewModel

class SettingViewModel : BaseViewModel<SettingState>(initialState = SettingState()) {

    @SuppressLint("CheckResult")
    fun getSettingList() {
        Single.just(
            listOf(
                SettingState.DividerVO,
                SettingState.CheckableVO(1, ICON_AUTO_CACHE, "自动缓存", AppRedux.instance.isAutoCache()),
                SettingState.CheckableVO(2, ICON_NO_IMAGE, "无图模式", AppRedux.instance.isNoImage()),
                SettingState.CheckableVO(3, ICON_DARK_MODE, "夜间模式", AppRedux.instance.isDarkMode()),
                SettingState.DividerVO,
                SettingState.DigitalVO(4, ICON_CLEAR_CACHE, "清除缓存", CacheUtil.instance.getCacheSize()),
                SettingState.DividerVO,
                SettingState.NavigableVO(5, ICON_FEEDBACK, "意见反馈"),
                SettingState.NavigableVO(6, ICON_ABOUT_US, "关于我们")
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