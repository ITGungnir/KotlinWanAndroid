package app.itgungnir.kwa.web

import app.itgungnir.kwa.R
import kotlinx.android.synthetic.main.activity_web.*
import my.itgungnir.apt.router.annotation.Route
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.rxmvvm.core.mvvm.BaseVM
import org.jetbrains.anko.share

@Route("web")
class WebActivity : BaseActivity<BaseVM>() {

    override fun contentView(): Int = R.layout.activity_web

    override fun obtainVM(): BaseVM? = null

    override fun initComponent() {

        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url").replace("http://", "https://")

        titleBar.title(title)
            .back { finish() }
            .addToolButton("\ue708") {}
            .addToolButton("\ue729") {
                share("KotlinWanAndroid分享《$title》专题：$url", title)
            }

        webView.loadUrl(url)
    }

    override fun observeVM() {
    }
}