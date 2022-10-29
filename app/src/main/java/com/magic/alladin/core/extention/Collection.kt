package com.magic.alladin.core.extention

import com.magic.alladin.modules.play.model.PlayModel
import java.util.*
import kotlin.collections.ArrayList

fun List<PlayModel>.copy(): List<PlayModel> {
    val items = arrayListOf<PlayModel>()
    forEach {
        items.add(
            PlayModel(
                it.id,
                it.position,
                it.isClose,
                it.closeCart,
                it.openCart,
                it.isSuccess,
                it.listener
            )
        )
    }
    return items
}

fun <T> ArrayList<T>.shuffle() {
    val random = Random()
    val n: Int = size

    // start from the beginning of the list

    // start from the beginning of the list
    for (i in 0 until n - 1) {
        // get a random index `j` such that `i <= j <= n`
        val j: Int = i + random.nextInt(n - i)

        // swap element at i'th position in the list with the element at
        // randomly generated index `j`
        val obj: T = get(i)
        set(i, get(j))
        set(j, obj)
    }
}