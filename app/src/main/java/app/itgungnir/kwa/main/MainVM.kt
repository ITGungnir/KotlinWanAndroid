package app.itgungnir.kwa.main

import android.arch.lifecycle.MutableLiveData
import app.itgungnir.kwa.common.widget.bottom_tab.BottomTab
import my.itgungnir.rxmvvm.core.mvvm.BaseVM
import org.joda.time.DateTime

class MainVM : BaseVM() {

    val finishState = MutableLiveData<Boolean>()

    private var lastTime = 0L

    fun tabs() = listOf(
        BottomTab("首页", "\ue703", "\ue702"),
        BottomTab("知识体系", "\ue6ef", "\ue6ee"),
        BottomTab("公众号", "\ue82c", "\ue608"),
        BottomTab("项目", "\ue6ec", "\ue6eb"),
        BottomTab("我的", "\ue716", "\ue715")
    )

    fun goBack() {
        val currTime = DateTime.now().millis
        if (currTime - lastTime < 1500) {
            this.finishState.value = true
        } else {
            this.lastTime = currTime
            this.finishState.value = false
        }
    }
}