package app.itgungnir.kwa.support.schedule.menu

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import app.itgungnir.kwa.support.R

class DropDownMenu @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    // 顶部菜单布局
    private val menuTabBar = MenuTabBar(context)
    // 底部容器，包含popupMenuViews，maskView
    private val containerView = FrameLayout(context)
    // 弹出菜单父布局
    private val popupMenuViews = FrameLayout(context)
    // 遮罩层
    private val maskView = View(context)

    // tabMenuView里面选中的tab位置，-1表示未选中
    private var currTabPosition = -1

    init {
        orientation = VERTICAL

        // 头栏
        menuTabBar.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(menuTabBar, 0)

        // 内容层
        containerView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        addView(containerView, 1)
    }

    /**
     * 初始化DropDownMenu
     */
    fun setDropDownMenu(popupViews: List<View>, contentView: View) {
        // Content
        containerView.addView(contentView, 0)
        // Mask
        maskView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        maskView.setBackgroundColor(Color.parseColor("#38000000"))
        maskView.setOnClickListener { closeMenu() }
        maskView.visibility = View.GONE
        containerView.addView(maskView, 1)
        // Menus
        if (containerView.getChildAt(2) != null) {
            containerView.removeViewAt(2)
        }
        popupMenuViews.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        popupMenuViews.visibility = View.GONE
        containerView.addView(popupMenuViews, 2)
        for (i in popupViews.indices) {
            popupViews[i].layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            popupMenuViews.addView(popupViews[i], i)
        }

        // 为tabBar设置点击事件
        menuTabBar.setClickCallback(
            { switchTab(0) },
            { switchTab(1) },
            { switchTab(2) }
        )
    }

    /**
     * 切换tab的文本和颜色
     */
    fun toggleTab(selected: Boolean, text: String? = null) {
        if (currTabPosition != -1) {
            menuTabBar.confirmTab(currTabPosition, selected, text)
            closeMenu()
        }
    }

    /**
     * 关闭菜单
     */
    fun closeMenu() {
        if (currTabPosition != -1) {
            menuTabBar.resetArrows()
            popupMenuViews.visibility = View.GONE
            popupMenuViews.animation = AnimationUtils.loadAnimation(context, R.anim.dd_menu_out)
            maskView.visibility = View.GONE
            maskView.animation = AnimationUtils.loadAnimation(context, R.anim.dd_mask_out)
            currTabPosition = -1
        }
    }

    /**
     * DropDownMenu是否处于可见状态
     */
    fun isShowing(): Boolean {
        return currTabPosition != -1
    }

    private fun switchTab(index: Int) {
        when {
            currTabPosition == -1 -> {
                popupMenuViews.visibility = View.VISIBLE
                popupMenuViews.animation = AnimationUtils.loadAnimation(context, R.anim.dd_menu_in)
                maskView.visibility = View.VISIBLE
                maskView.animation = AnimationUtils.loadAnimation(context, R.anim.dd_mask_in)
                for (i in 0 until popupMenuViews.childCount) {
                    if (i == index) {
                        popupMenuViews.getChildAt(i).visibility = View.VISIBLE
                    } else {
                        popupMenuViews.getChildAt(i).visibility = View.GONE
                    }
                }
                currTabPosition = index
            }
            index != currTabPosition -> {
                menuTabBar.resetArrows(index)
                for (i in 0 until popupMenuViews.childCount) {
                    if (i == index) {
                        popupMenuViews.getChildAt(i).visibility = View.VISIBLE
                    } else {
                        popupMenuViews.getChildAt(i).visibility = View.GONE
                    }
                }
                currTabPosition = index
            }
            else -> closeMenu()
        }
    }
}