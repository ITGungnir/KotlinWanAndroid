package app.itgungnir.kwa.common.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> fromJson(json: String?): T? =
    Gson().fromJson(json, object : TypeToken<T>() {}.type)

fun toJson(src: Any): String =
    Gson().toJson(src)
