package com.magic.alladin.modules.play.repository

import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toBitmap
import com.magic.alladin.R
import com.magic.alladin.core.extention.copy
import com.magic.alladin.core.repository.ResManager
import com.magic.alladin.modules.play.model.PlayModel
import kotlin.random.Random

interface PlayFieldRepository {

    suspend fun playField(size: Int): List<PlayModel>

    class Base(private val resManager: ResManager) : PlayFieldRepository {

        override suspend fun playField(size: Int): List<PlayModel> {
            val fields = arrayListOf<PlayModel>()

            val closeCart = resManager.drawable(R.drawable.emty_cart)!!.toBitmap()
            val mSize = size / 2

            for (i in 0 until mSize) {
                val model = PlayModel(
                    id = i,
                    isClose = true,
                    openCart = BitmapFactory.decodeStream(resManager.imageFileFromAssets("carts/group_${i + 1}.png")),
                    closeCart = closeCart
                )
                fields.add(model)
            }
            val newField = fields.copy()
            fields.addAll(newField)
            fields.shuffle()
            for (i in 0 until size) {
                fields[i].position = i
            }
            return fields
        }
    }

}