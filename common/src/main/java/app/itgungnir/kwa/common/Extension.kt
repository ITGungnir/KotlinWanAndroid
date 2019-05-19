package app.itgungnir.kwa.common

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import app.itgungnir.kwa.common.util.GlideApp
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.view.RxView
import org.jetbrains.anko.contentView
import java.util.concurrent.TimeUnit

/**
 * dp转px
 */
fun Context.dp2px(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

/**
 * Activity - 弹出SnackBar
 */
fun AppCompatActivity.popToast(content: String) {
    Snackbar.make(contentView!!, content, Snackbar.LENGTH_SHORT).show()
}

/**
 * Fragment - 弹出SnackBar
 */
fun Fragment.popToast(content: String) {
    Snackbar.make(view!!, content, Snackbar.LENGTH_SHORT).show()
}

/**
 * 加载网络图片到ImageView中
 */
fun ImageView.load(url: String) =
    GlideApp.with(this.context)
        .load(url.replaceFirst("http://", "https://"))
        .placeholder(R.mipmap.img_placeholder)
        .error(R.mipmap.img_placeholder)
        .centerCrop()
        .into(this)

/**
 * 加载本地res文件夹中的图片到ImageView中（目前仅供无图模式下使用）
 */
fun ImageView.load(imgRes: Int) =
    GlideApp.with(this.context)
        .load(imgRes)
        .placeholder(R.mipmap.img_placeholder)
        .error(R.mipmap.img_placeholder)
        .centerCrop()
        .into(this)

/**
 * 隐藏软键盘
 */
fun View.hideSoftInput() =
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(windowToken, 0)

/**
 * 防抖动的点击事件
 */
fun View.onAntiShakeClick(block: (View) -> Unit) =
    RxView.clicks(this)
        .throttleFirst(2L, TimeUnit.SECONDS)
        .subscribe { block.invoke(this) }!!

/**
 * 加载HTML代码到TextView中
 */
fun html(html: String): String = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

/**
 * 获取XML配置中的颜色
 */
fun Context.color(id: Int) = ContextCompat.getColor(this, id)
