package app.itgungnir.kwa.web

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun initComponent() {

        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url").replace("http://", "https://")

        titleBar.title(title)
            .back { finish() }
            .addToolButton("\ue708") {}
            .addToolButton("\ue729") {
                share("KotlinWanAndroid分享《$title》专题：$url", title)
            }

        browser.apply {
            settings.apply {
                javaScriptEnabled = true
                blockNetworkImage = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
            }
            webViewClient = object : WebViewClient() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                    view.loadUrl(request.url.toString())
                    return true
                }

                @Suppress("OverridingDeprecatedMember")
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
            }
            loadUrl(url)
        }
    }
}