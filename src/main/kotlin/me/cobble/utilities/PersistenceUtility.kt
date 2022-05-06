package me.cobble.utilities

import java.io.File

class PersistenceUtility {

    companion object {
        fun save(data: MutableMap<String, String>) {
            for ((key, value) in data) {
                File("stickyMessages.txt").writeText("$key,$value")
            }
            println("Saved")
        }

        fun load(): MutableMap<String, String> {
            val data = mutableMapOf<String,String>()
            val file = File("stickyMessages.txt")
            if (file.exists()) {
                file.forEachLine {
                    val split = it.split(",")
                    data[split[0]] = split[1]
                }
            } else {
                file.createNewFile()
            }
            return data
        }
    }
}