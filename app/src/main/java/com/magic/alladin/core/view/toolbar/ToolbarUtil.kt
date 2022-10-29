package com.magic.alladin.core.view.toolbar

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.magic.alladin.R

class ToolbarUtil(private val v: View, private val appCompatActivity: AppCompatActivity) {

    private var toolbar: Toolbar? = null

    fun init(@IdRes id: Int = R.id.base_toolbar) {
        toolbar = v.findViewById(id)
        appCompatActivity.setSupportActionBar(toolbar)
    }

    fun setTitle(title: String?) {
        appCompatActivity.supportActionBar?.title = title
    }

    fun setDisplayHomeEnable(enable: Boolean) {
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }

    fun setTitleTextAppearance(@StyleRes style: Int = R.style.CustomToolBarStyle) {
        toolbar?.setTitleTextAppearance(appCompatActivity, style)
    }

    fun changeControlColor(@ColorInt color: Int = Color.WHITE) {
        toolbar?.children?.forEach {
            (it as? AppCompatImageButton)?.imageTintList =
                ColorStateList.valueOf(color)
            it.refreshDrawableState()
        }
    }
}