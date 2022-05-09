package me.cobble.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.time.Duration
import java.time.LocalDateTime

class PingCommand(api: JDA) : ListenerAdapter() {

    init {
        api.addEventListener(this)
        api.upsertCommand("ping", "Pong!").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "ping") {
            val botPing = Duration.between(interaction.timeCreated.toLocalDateTime(), LocalDateTime.now())
            interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle("Pong!")
                    .setDescription("The bot is alive!")
                    .addField("API Latency", "${event.jda.gatewayPing}ms", false)
                    .addField("Bot Latency", "${botPing.toMillis()}ms", false)
                    .setColor(Color.GREEN)
                    .build()
            ).complete()
        }
    }
}