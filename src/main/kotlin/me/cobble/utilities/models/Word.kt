package me.cobble.utilities.models

import me.cobble.utilities.WordGameUtil

class Word(val word: String, val wordDifficulty: WordGameUtil.WordDifficulty, val definition: String?) {

    var placeholder = StringBuilder()
    private var guesses = if (wordDifficulty == WordGameUtil.WordDifficulty.EASY) 5 else 3

    /**
     * @return true if letter exists
     */
    fun solveLetter(char: Char): Boolean {
        return if (word.contains(char)) {
            word.forEachIndexed { i, _ ->
                if (word[i] == char) {

                    placeholder[(i * 2) - 1] = ' '
                    placeholder[i * 2] = char.uppercaseChar()
                }
            }
            true
        } else {
            false
        }
    }

    fun solveWord(string: String): Boolean {
        return string == word
    }

    fun createBlankPlaceholder() {
        word.forEach { _ -> placeholder.append('\\').append('_') }
    }

    fun isSolved(): Boolean {
        return placeholder.toString() == word.uppercase()
    }

    fun getGuesses(): Int {
        return guesses
    }

    fun setGuesses(remainingGuesses: Int) {
        guesses = remainingGuesses
    }
}
