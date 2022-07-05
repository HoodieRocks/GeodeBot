package me.cobble.utilities

object Utils {

    fun stripQuotation(str: String): String {
        return str.replace("\"", "")
    }
}