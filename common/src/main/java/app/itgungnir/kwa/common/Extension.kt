package app.itgungnir.kwa.common

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import app.itgungnir.kwa.common.util.GlideApp
import com.google.android.material.snackbar.Snackbar
import my.itgungnir.ui.color
import my.itgungnir.ui.dialog.SimpleDialog
import my.itgungnir.ui.list_footer.FooterStatus
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.contentView
import org.jetbrains.anko.textColor

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
 * 弹出SimpleDialog
 */
fun Context.simpleDialog(manager: FragmentManager, msg: String, onConfirm: (() -> Unit)? = null) =
    SimpleDialog.Builder()
        .backgroundColor(this.color(R.color.clr_dialog), 5F)
        .dividerColor(this.color(R.color.clr_divider))
        .message(msg, this.color(R.color.text_color_level_2))
        .confirm { onConfirm?.invoke() }
        .create()
        .show(manager, SimpleDialog::class.java.name)

/**
 * 根据状态渲染ListFooter的UI
 */
fun renderFooter(view: View, status: FooterStatus.Status) {
    view.backgroundColor = view.context.color(R.color.clr_divider)
    val title = view.findViewById<TextView>(R.id.footerTitle)
    val progress = view.findViewById<ProgressBar>(R.id.footerProgress)
    title.textColor = view.context.color(R.color.clr_background)
    when (status) {
        FooterStatus.Status.PROGRESSING -> {
            title.visibility = View.GONE
            progress.visibility = View.VISIBLE
        }
        FooterStatus.Status.SUCCEED -> {
            title.visibility = View.VISIBLE
            progress.visibility = View.GONE
            title.text = "加载成功"
        }
        FooterStatus.Status.NO_MORE -> {
            title.visibility = View.VISIBLE
            progress.visibility = View.GONE
            title.text = "我是有底线的"
        }
        FooterStatus.Status.FAILED -> {
            title.visibility = View.VISIBLE
            progress.visibility = View.GONE
            title.text = "加载失败，请重试"
        }
    }
}
