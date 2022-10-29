package com.magic.alladin.core.extention

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.magic.alladin.modules.play.model.PlayModel

fun ImageView.openCart(duration: Long = 400, listener: Animator.AnimatorListener? = null) {
    startAnimation(deg = 0f, duration = duration, listener)
}

fun ImageView.closeCart(duration: Long = 400, listener: Animator.AnimatorListener? = null) {
    startAnimation(deg = 90f, duration = duration, listener)
}

fun ImageView.openCloseCard(
    model: PlayModel,
    duration: Long = 500, endAnimation: (() -> Unit)? = null
) {
    if (model.isClose) {
        closeCart(duration = duration, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                setImageBitmap(model.closeCart)
                openCart(duration = duration, object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        endAnimation?.invoke()
                    }
                })
            }
        })
    } else {
        closeCart(duration = duration, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                setImageBitmap(model.openCart)
                openCart(duration = duration, object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        endAnimation?.invoke()
                    }
                })
            }
        })
    }
}

fun ImageView.startAnimation(
    deg: Float,
    duration: Long = 400,
    listener: Animator.AnimatorListener? = null
) {
    val animator = animate().rotationY(deg).setDuration(duration)
    listener?.let {
        animator.setListener(it)
    }
    animator.start()
}

fun View.margins(left: Int = marginLeft, top: Int = marginTop, right: Int = marginRight, bottom: Int = marginBottom) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val p = layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        requestLayout()
    }
}