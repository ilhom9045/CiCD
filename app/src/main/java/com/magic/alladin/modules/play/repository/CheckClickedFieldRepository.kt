package com.magic.alladin.modules.play.repository

import com.magic.alladin.modules.play.callBack.CheckedFieldListener
import com.magic.alladin.modules.play.model.CheckedFieldDataModel
import com.magic.alladin.modules.play.model.PlayModel
import kotlin.properties.Delegates.notNull

interface CheckClickedFieldRepository {

    fun clockedItems(item: PlayModel, checkedFieldListener: CheckedFieldListener)

    class Base : CheckClickedFieldRepository {

        private var clazz: Check? = null

        override fun clockedItems(item: PlayModel, checkedFieldListener: CheckedFieldListener) {
            if (clazz == null) {
                clazz = Check()
            }
            clazz?.check(item, checkedFieldListener)
        }

        private inner class Check {

            var step: Int = 1

            private var firstFieldModel: PlayModel? = null

            fun check(item: PlayModel, checkedFieldListener: CheckedFieldListener) {
                if (step == 1) {
                    firstFieldModel = item
                    step++
                } else {
                    checkedFieldListener.checkedField(
                        CheckedFieldDataModel(
                            firstFieldModel!!,
                            item,
                            firstFieldModel!!.id == item.id
                        )
                    )
                    firstFieldModel = null
                    clazz = null
                }
            }
        }

    }
}