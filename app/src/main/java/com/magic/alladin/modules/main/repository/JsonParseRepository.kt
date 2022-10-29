package com.magic.alladin.modules.main.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface JsonParseRepository {

    fun toJson(clazz: Any?): String?

    fun <T : Any> fromJson(json: String, clazz: Class<T>): T

    class Base : JsonParseRepository {

        private val gson = Gson()

        override fun toJson(clazz: Any?): String {
            return gson.toJson(clazz)
        }

        override fun <T : Any> fromJson(json: String, clazz: Class<T>): T {
            return gson.fromJson(json, clazz)
        }
    }
}

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)