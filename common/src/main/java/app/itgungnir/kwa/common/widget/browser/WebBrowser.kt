package app.itgungnir.kwa.common.widget.browser

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import app.itgungnir.kwa.common.R
import kotlinx.android.synthetic.main.view_web_browser.view.*

@SuppressLint("SetJavaScriptEnabled")
class WebBrowser @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var browserView: WebView

    private var progressView: ProgressBar

    init {

        View.inflate(context, R.layout.view_web_browser, this)

        browserView = webView
        progressView = progressBar

        browserView.apply {
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
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    progressView.apply {
                        progress = newProgress
                        visibility = when (newProgress) {
                            100 -> View.GONE
                            else -> View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    fun load(url: String) {
        browserView.loadUrl(url)
    }
}