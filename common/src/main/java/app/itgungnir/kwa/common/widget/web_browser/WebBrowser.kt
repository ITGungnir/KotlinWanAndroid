package app.itgungnir.kwa.common.widget.web_browser

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import app.itgungnir.kwa.common.R
import kotlinx.android.synthetic.main.view_web_browser.view.*

class WebBrowser @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_web_browser, this)

        webView.apply {
            webViewClient = kwaWebViewClient
            webChromeClient = kwaWebChromeClient
        }
    }

    private val kwaWebViewClient = object : WebViewClient() {
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

    private val kwaWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            when (newProgress) {
                100 -> {
                    progressLine.visibility = View.GONE
                }
                else -> {
                    progressLine.apply {
                        visibility = View.VISIBLE
                        layoutParams.width = this@WebBrowser.width / 100 * newProgress
                    }
                }
            }
        }
    }

    fun loadUrl(url: String) {
        webView.loadUrl(url)
    }
}