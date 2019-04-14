package app.itgungnir.kwa.web

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.itgungnir.kwa.R
import kotlinx.android.synthetic.main.activity_web.*
import my.itgungnir.apt.router.annotation.Route
import org.jetbrains.anko.share

@Route("web")
class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        initComponent()
    }

    private fun initComponent() {

        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url").replace("http://", "https://")

        titleBar.title(title)
            .back { finish() }
            .addToolButton("\ue708") {}
            .addToolButton("\ue729") {
                share("KotlinWanAndroid分享《$title》专题：$url", title)
            }

        webBrowser.loadUrl(url)
    }
}