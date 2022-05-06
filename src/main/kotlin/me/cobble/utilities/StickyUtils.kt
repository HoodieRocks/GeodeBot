package me.cobble.utilities

class StickyUtils {

    companion object {
        private val stickyMessages: MutableMap<String, String> = mutableMapOf()

        fun getStickyMessage(key: String): String {
            return stickyMessages[key] ?: ""
        }

        fun setStickyMessage(key: String, value: String) {
            stickyMessages[key] = value
        }

        fun removeStickyMessage(key: String) {
            stickyMessages.remove(key)
        }

        fun getAllStickyMessages(): MutableMap<String, String> {
            return stickyMessages
        }

        fun setAllStickyMessages(stickyMessages: MutableMap<String, String>) {
            this.stickyMessages.clear()
            this.stickyMessages.putAll(stickyMessages)
        }
    }
}