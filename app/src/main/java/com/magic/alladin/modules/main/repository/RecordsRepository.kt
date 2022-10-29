package com.magic.alladin.modules.main.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.magic.alladin.modules.main.model.RecordsModel

interface RecordsRepository {

    fun records(): ArrayList<RecordsModel>

    fun saveRecords(records: ArrayList<RecordsModel>)

    class Base(
        private val context: Context,
        private val jsonParseRepository: JsonParseRepository = JsonParseRepository.Base()
    ) : RecordsRepository {
        val sharedPreferences =
            context.getSharedPreferences(this::class.java.simpleName, Context.MODE_PRIVATE)

        override fun records(): ArrayList<RecordsModel> {
            val json = sharedPreferences.getString(RECORDS, null)
            val records = ArrayList<RecordsModel>()
            json?.let {
                records.addAll(Gson().fromJson(it))
            }
            return records
        }

        override fun saveRecords(records: ArrayList<RecordsModel>) {
            val json = jsonParseRepository.toJson(records)
            sharedPreferences.edit().putString(RECORDS, json).apply()
        }


        private companion object {
            const val RECORDS = "records"
        }

    }

}