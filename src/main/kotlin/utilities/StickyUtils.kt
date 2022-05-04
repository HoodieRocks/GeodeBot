package utilities

class StickyUtils {

    companion object {
        private val stickyMessages: MutableMap<Long, Long> = mutableMapOf()

        fun getStickyMessage(key: Long): Long {
            return stickyMessages[key] ?: 0
        }

        fun setStickyMessage(key: Long, value: Long) {
            stickyMessages[key] = value
        }

        fun removeStickyMessage(key: Long) {
            stickyMessages.remove(key)
        }

        fun getAllStickyMessages(): MutableMap<Long, Long> {
            return stickyMessages
        }

        fun setAllStickyMessages(stickyMessages: MutableMap<Long, Long>) {
            this.stickyMessages.clear()
            this.stickyMessages.putAll(stickyMessages)
        }
    }
}