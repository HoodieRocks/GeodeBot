package me.cobble.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class HelpCommand(private val api: JDA) : ListenerAdapter() {

    init {
        api.addEventListener(this)
        api.upsertCommand("help", "List commands and features of Amethyst").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "help") {
            val embed = EmbedBuilder()

            embed.setTitle("All commands")
            api.retrieveCommands().complete().forEach {
                embed.addField(it.name, "`${it.description}`", false)
            }
            embed.setColor(Color.MAGENTA)
            interaction.replyEmbeds(embed.build()).queue()
        }
    }
}