package com.magic.alladin.modules.play.model

import android.graphics.Bitmap
import com.magic.alladin.modules.play.callBack.UpdateItem

data class PlayModel(
    var id: Int,
    var position: Int = 1,
    var isClose: Boolean,
    val closeCart: Bitmap,
    val openCart: Bitmap,
    var isSuccess: Boolean = false,
    var listener: UpdateItem? = null
)
