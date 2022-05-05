package me.cobble.utilities

import java.io.File

class PersistenceUtility {

    companion object {
        fun save(data: MutableMap<Long, Long>) {
            for ((key, value) in data) {
                File("stickyMessages.txt").writeText("$key,$value")
            }
            println("Saved")
        }

        fun load(): MutableMap<Long, Long> {
            val data = mutableMapOf<Long, Long>()
            val file = File("stickyMessages.txt")
            if (file.exists()) {
                file.forEachLine {
                    val split = it.split(",")
                    data[split[0].toLong()] = split[1].toLong()
                }
            } else {
                file.createNewFile()
            }
            return data
        }
    }
}