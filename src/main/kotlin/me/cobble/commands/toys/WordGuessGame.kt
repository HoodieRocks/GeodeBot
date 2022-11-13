package me.cobble.commands.toys

import kotlinx.serialization.json.jsonObject
import me.cobble.utilities.Utils
import me.cobble.utilities.WordGameUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.text.TextInput
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.interactions.modals.Modal

class WordGuessGame(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build()
            .upsertCommand("wordgame", "Fun word guessing game!")
            .addSubcommands(
                SubcommandData("end", "Ends your existing game"),
                SubcommandData("start", "Starts a game")
            )
            .queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "wordgame") {
            if (interaction.subcommandName == "start") {
                interaction.replyEmbeds(
                    EmbedBuilder()
                        .setTitle(":abc: Word Game")
                        .setDescription("Please select your difficulty")
                        .setColor(0xccaed9)
                        .build()
                ).setEphemeral(true)
                    .addActionRow(Button.success("easy", "Easy"), Button.danger("hard", "Hard"))
                    .queue()
            } else if (interaction.subcommandName == "end") {
                WordGameUtil.endGame(event.user.idLong)
                interaction.reply("Game Ended!")
            }
        }
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        event.message.editMessageComponents()
        val json = Utils.makeGetRequestAsArray("https://random-words-api.vercel.app/word")
        val embed = EmbedBuilder()
        val word = json[0].jsonObject["word"].toString()
        when (event.componentId) {
            "easy" -> {
                if (WordGameUtil.isActiveGameRunning(event.user.idLong)) {
                    event.interaction.replyEmbeds(
                        embed
                            .setTitle("Game Already Running!")
                            .setDescription("There is already a running game for you, you can end game by running /wordgame end")
                            .build()
                    ).setEphemeral(true).queue()
                }
                WordGameUtil.createGame(
                    event.user.idLong,
                    word,
                    json[0].jsonObject["definition"].toString(),
                    WordGameUtil.WordDifficulty.EASY
                )
                val placeholder = WordGameUtil.getWord(event.user.idLong).placeholder

                event.interaction.replyEmbeds(
                    embed
                        .setTitle("Guess the Word: Easy")
                        .addField("Word", placeholder.toString(), false)
                        .addField("Definition", json[0].jsonObject["definition"].toString(), false)
                        .build()
                ).setEphemeral(true)
                    .addActionRow(Button.primary("guess", "Guess"))
                    .queue()
            }

            "guess" -> {
                val guess: TextInput = TextInput.create("guess", "Guess", TextInputStyle.SHORT)
                    .setPlaceholder("Your Guess")
                    .setMinLength(1)
                    .setMaxLength(100)
                    .build()

                val modal: Modal = Modal.create("guessWindow", "Guess Window")
                    .addActionRows(ActionRow.of(guess))
                    .build()

                event.replyModal(modal).queue()
            }

            else -> {
                WordGameUtil.createGame(event.user.idLong, word, null, WordGameUtil.WordDifficulty.HARD)
                val placeholder = WordGameUtil.getWord(event.user.idLong).placeholder

                event.interaction.replyEmbeds(
                    embed
                        .setTitle("Guess the Word: Hard")
                        .addField("Word", placeholder.toString(), false)
                        .build()
                ).setEphemeral(true)
                    .addActionRow(Button.primary("guess", "Guess"))
                    .queue()
            }
        }
    }

    override fun onModalInteraction(event: ModalInteractionEvent) {
        if (event.modalId == "guessWindow") {
            val guess = event.getValue("guess")!!.asString
            val id = event.user.idLong
            val result = WordGameUtil.guess(id, guess)
            val word = WordGameUtil.getWord(id)

            if (!WordGameUtil.isActiveGameRunning(event.user.idLong)) {
                event.interaction.replyEmbeds(
                    EmbedBuilder()
                        .setTitle("No Game")
                        .setDescription("You have no active game running!")
                        .build()
                ).queue()
                return
            } else {

                if (result == WordGameUtil.GuessResult.FAIL_WORD || word.getGuesses() <= 0) {
                    event.interaction.replyEmbeds(
                        EmbedBuilder()
                            .setTitle("You Lost")
                            .addField("The word was", word.word, false)
                            .build()
                    ).setEphemeral(true).queue()
                    WordGameUtil.endGame(event.user.idLong)
                    return
                }
                if (result == WordGameUtil.GuessResult.FAIL_LETTER) {
                    val difficulty = word.wordDifficulty
                    word.setGuesses(word.getGuesses() - 1)

                    if (difficulty == WordGameUtil.WordDifficulty.EASY) {
                        event.interaction.editMessageEmbeds(
                            EmbedBuilder()
                                .setTitle("Guess the word: Easy")
                                .addField("Word", word.placeholder.toString(), false)
                                .addField("Definition", word.definition!!, false)
                                .addField("Guesses Left", word.getGuesses().toString(), false)
                                .build()
                        ).queue()
                        return
                    } else event.interaction.editMessageEmbeds(
                        EmbedBuilder()
                            .setTitle("Guess the word: Hard")
                            .addField("Word", word.placeholder.toString(), false)
                            .addField("Guesses Left", word.getGuesses().toString(), false)
                            .build()
                    ).queue()
                    return
                }

                if (result == WordGameUtil.GuessResult.SUCCESS_WORD || word.isSolved()) {
                    event.interaction.replyEmbeds(
                        EmbedBuilder()
                            .setTitle("You Win :tada:")
                            .addField("The word was", word.word, false)
                            .build()
                    ).setEphemeral(true).queue()
                    WordGameUtil.endGame(event.user.idLong)
                    return
                }
                if (result == WordGameUtil.GuessResult.SUCCESS_LETTER) {
                    val difficulty = word.wordDifficulty

                    if (difficulty == WordGameUtil.WordDifficulty.EASY) event.interaction.editMessageEmbeds(
                        EmbedBuilder()
                            .setTitle("Guess the word: Easy")
                            .addField("Word", word.placeholder.toString(), false)
                            .addField("Definition", word.definition!!, false)
                            .addField("Guesses Left", word.getGuesses().toString(), false)
                            .build()
                    ).queue()
                    else event.interaction.editMessageEmbeds(
                        EmbedBuilder()
                            .setTitle("Guess the word: Hard")
                            .addField("Word", word.placeholder.toString(), false)
                            .addField("Guesses Left", word.getGuesses().toString(), false)
                            .build()
                    ).queue()
                }
            }
        }
    }
}