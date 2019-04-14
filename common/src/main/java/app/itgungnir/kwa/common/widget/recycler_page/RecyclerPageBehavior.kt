package app.itgungnir.kwa.common.widget.recycler_page

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View

class RecyclerPageBehavior : FloatingActionButton.Behavior {

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var currScale = 0F

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        layoutDirection: Int
    ): Boolean {
        child.scaleX = currScale
        child.scaleY = currScale
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean = true

    @SuppressLint("RestrictedApi")
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type
        )
        if (dyConsumed >= 0) {
            if (currScale == 1F) {
                val x = ObjectAnimator.ofFloat(child, "scaleX", 1F, 0F)
                val y = ObjectAnimator.ofFloat(child, "scaleY", 1F, 0F)
                AnimatorSet().apply {
                    play(x).with(y)
                    duration = 250
                }.start()
                currScale = 0F
            }
        } else {
            if (currScale == 0F) {
                val x = ObjectAnimator.ofFloat(child, "scaleX", 0F, 1F)
                val y = ObjectAnimator.ofFloat(child, "scaleY", 0F, 1F)
                AnimatorSet().apply {
                    play(x).with(y)
                    duration = 250
                }.start()
                currScale = 1F
            }
        }
    }
}