package me.cobble.utilities

import me.cobble.utilities.models.Word

object WordGameUtil {

    private val wordMap = hashMapOf<Long, Word>()

    enum class GuessResult {
        SUCCESS_LETTER,
        SUCCESS_WORD,
        FAIL_LETTER,
        FAIL_WORD,
    }

    enum class WordDifficulty {
        EASY,
        HARD
    }

    fun createGame(userId: Long, string: String, definition: String?, wordDifficulty: WordDifficulty) {
        val word = Word(string, wordDifficulty, definition)
        word.createBlankPlaceholder()
        wordMap[userId] = word
    }

    fun endGame(userId: Long) {
        wordMap.remove(userId)
    }

    fun isActiveGameRunning(userId: Long): Boolean {
        return wordMap.contains(userId)
    }

    fun getWord(userId: Long): Word = wordMap[userId]!!

    fun guess(userId: Long, string: String): GuessResult {
        return if (string.length == 1) {
            if (wordMap[userId]!!.solveLetter(string[0])) GuessResult.SUCCESS_LETTER
            else GuessResult.FAIL_LETTER
        } else {
            if (wordMap[userId]!!.solveWord(string)) GuessResult.SUCCESS_WORD
            else GuessResult.FAIL_WORD
        }
    }
}