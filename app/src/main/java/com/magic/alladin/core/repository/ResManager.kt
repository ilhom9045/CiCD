package com.magic.alladin.core.repository

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.io.InputStream

interface ResManager {

    fun drawable(@DrawableRes id: Int): Drawable?

    fun imageFileFromAssets(name: String): InputStream

    class Base(private val context: Context) : ResManager {

        override fun drawable(id: Int): Drawable? {
            return ContextCompat.getDrawable(context,id)
        }

        override fun imageFileFromAssets(name: String): InputStream {
            return context.assets.open(name)
        }

    }

}