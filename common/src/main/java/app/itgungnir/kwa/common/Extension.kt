package app.itgungnir.kwa.common

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.itgungnir.kwa.common.util.GlideApp
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.contentView

/**
 * dp转px
 */
fun Context.dp2px(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

/**
 * 弹出SnackBar
 */
fun AppCompatActivity.popToast(content: String) {
    Snackbar.make(contentView!!, content, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.popToast(content: String) {
    Snackbar.make(view!!, content, Snackbar.LENGTH_SHORT).show()
}

/**
 * 加载网络图片到ImageView中
 */
fun ImageView.load(url: String) =
    GlideApp.with(this.context)
        .load(url)
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
