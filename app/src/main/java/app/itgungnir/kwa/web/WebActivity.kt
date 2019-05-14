package app.itgungnir.kwa.web

import androidx.lifecycle.Observer
import app.itgungnir.kwa.R
import app.itgungnir.kwa.common.*
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.AppState
import kotlinx.android.synthetic.main.activity_web.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.grouter.api.Router
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import org.jetbrains.anko.share

@Route(WebActivity)
class WebActivity : BaseActivity() {

    private var id: Int = -1
    private var originId: Int = -1

    private val viewModel by lazy {
        buildActivityViewModel(
            activity = this,
            viewModelClass = WebViewModel::class.java
        )
    }

    override fun layoutId(): Int = R.layout.activity_web

    override fun initComponent() {

        id = intent.getIntExtra("id", -1)
        originId = intent.getIntExtra("originId", -1)
        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url").replace("http://", "https://")

        headBar.title(title)
            .back(ICON_BACK) { finish() }

        if (id > 0 && originId >= 0) {
            headBar.addToolButton(ICON_UNCOLLECT) {
                if (AppRedux.instance.isUserIn()) {
                    when (AppRedux.instance.isCollected(id) || AppRedux.instance.isCollected(originId)) {
                        // 取消收藏
                        true -> when (originId) {
                            0 -> viewModel.disCollectInnerArticle(id)
                            else -> viewModel.disCollectInnerArticle(originId)
                        }
                        // 收藏
                        else -> when (originId) {
                            0 -> viewModel.collectInnerArticle(id)
                            else -> viewModel.collectInnerArticle(originId)
                        }
                    }
                } else {
                    Router.instance.with(this)
                        .target(LoginActivity)
                        .go()
                }
            }
        }

        headBar.addToolButton(ICON_SHARE) {
            share("KotlinWanAndroid分享《$title》专题：$url", title)
        }

        browser.load(url)
    }

    override fun observeVM() {

        AppRedux.instance.pick(AppState::collectIds)
            .observe(this, Observer { collectIds ->
                collectIds?.a?.let {
                    if (headBar.toolButtonCount() > 1) {
                        val flag = it.contains(id) || it.contains(originId)
                        headBar.updateToolButton(0, if (flag) ICON_COLLECT else ICON_UNCOLLECT)
                    }
                }
            })

        viewModel.pick(WebState::error)
            .observe(this, Observer { error ->
                error?.a?.message?.let {
                    popToast(it)
                }
            })
    }
}