package com.magic.alladin.core.view.model

data class InputDialogAmountModel(
    val nameStr: String? = null,
    val cancelButtonText: String? = null,
    val buttonText: String,
    val action: ((name: String?) -> Unit)?,
    val cancelAction: (() -> Unit)? = null
)