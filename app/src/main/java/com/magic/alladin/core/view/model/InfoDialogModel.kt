package com.magic.alladin.core.view.model

import android.graphics.drawable.Drawable

data class InfoDialogModel(
    val drawable: Drawable? = null,
    val title: String? = null,
    var message: String? = null,
    var buttonText: String,
    var cancelButtonText: String? = null,
    var action: (() -> Unit)? = null,
    val cancelAction: (() -> Unit)? = null,
    val spannableString: String? = null
)