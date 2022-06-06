package me.cobble.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import kotlin.math.floor

class PasswordGenCommand(api: JDABuilder): ListenerAdapter() {

    init {
        api.build().upsertCommand("password", "Generates a password (DO NOT ACTUALLY USE!)")
            .addOption(OptionType.INTEGER, "length", "The length of the password to generate", false)
            .queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "password") {
            interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle("Password")
                    .setDescription("Your generated password: `${generatePassword(interaction.getOption("length")?.asInt ?: 8)}`")
                    .setColor(0xCDCDCD)
                    .build()
            ).queue()
        }
    }

    private fun generatePassword(length: Int?): String {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+"
        val pass = StringBuilder()
        for (i in 0 until length!!) {
            pass.append(chars[floor(Math.random() * chars.length).toInt()])
        }
        return pass.toString()
    }
}