package me.cobble.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class PingCommand(api: JDA) : ListenerAdapter() {

    init {
        api.addEventListener(this)
        api.upsertCommand("ping", "Pong!").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "ping") {
            interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle("Pong!")
                    .setDescription("The bot is alive!")
                    .setColor(Color.GREEN)
                    .build()
            ).complete()
        }
    }
}