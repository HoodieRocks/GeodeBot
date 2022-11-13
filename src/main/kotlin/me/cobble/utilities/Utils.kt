package me.cobble.utilities

import kotlinx.serialization.json.*
import okhttp3.OkHttpClient
import okhttp3.Request

object Utils {

    fun stripQuotation(str: String): String {
        return str.replace("\"", "")
    }

    fun makeGetRequest(url: String): JsonObject {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        return Json.parseToJsonElement(response.body!!.string()).jsonObject
    }

    fun makeGetRequestAsArray(url: String): JsonArray {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        return Json.parseToJsonElement(response.body!!.string()).jsonArray
    }
}