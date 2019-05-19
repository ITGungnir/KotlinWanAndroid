package app.itgungnir.kwa.support.web

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import app.itgungnir.kwa.common.LoginActivity
import app.itgungnir.kwa.common.WebActivity
import app.itgungnir.kwa.common.html
import app.itgungnir.kwa.common.popToast
import app.itgungnir.kwa.common.redux.AppRedux
import app.itgungnir.kwa.common.redux.AppState
import app.itgungnir.kwa.support.R
import kotlinx.android.synthetic.main.activity_web.*
import my.itgungnir.grouter.annotation.Route
import my.itgungnir.grouter.api.Router
import my.itgungnir.rxmvvm.core.mvvm.BaseActivity
import my.itgungnir.rxmvvm.core.mvvm.buildActivityViewModel
import my.itgungnir.ui.browser.WebBrowser
import my.itgungnir.ui.input.ProgressButton
import my.itgungnir.ui.status_view.StatusView
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

        headBar.title(html(title))
            .back(getString(R.string.icon_back)) { finish() }

        if (id > 0 && originId >= 0) {
            headBar.addToolButton(getString(R.string.icon_uncollect)) {
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

        headBar.addToolButton(getString(R.string.icon_share)) {
            share("KotlinWanAndroid分享《$title》专题：$url", title)
        }

        statusView.addDelegate(StatusView.Status.SUCCEED, R.layout.view_status_web) {
            loadSucceedPage(it, url)
        }.addDelegate(StatusView.Status.FAILED, R.layout.view_status_error) {
            loadFailedPage(it, url)
        }.succeed { }
    }

    @SuppressLint("SetTextI18n")
    private fun loadSucceedPage(view: View, url: String) {
        view.findViewById<WebBrowser>(R.id.browser).load(url, AppRedux.instance.isNoImage())
            .onError { code, msg ->
                statusView.failed { view ->
                    view.findViewById<TextView>(R.id.tip).text = "$code：$msg"
                    view.findViewById<ProgressButton>(R.id.button).ready("重新加载")
                }
            }
    }

    private fun loadFailedPage(view: View, url: String) {
        view.findViewById<ProgressButton>(R.id.button).apply {
            ready("重新加载")
            setOnClickListener {
                loading()
                statusView.succeed { v -> loadSucceedPage(v, url) }
            }
        }
    }

    override fun observeVM() {

        AppRedux.instance.pick(AppState::collectIds)
            .observe(this, Observer { collectIds ->
                collectIds?.a?.let {
                    if (headBar.toolButtonCount() > 1) {
                        val flag = it.contains(id) || it.contains(originId)
                        headBar.updateToolButton(
                            0,
                            when (flag) {
                                true -> getString(R.string.icon_collect)
                                else -> getString(R.string.icon_uncollect)
                            }
                        )
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