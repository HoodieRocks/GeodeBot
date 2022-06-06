package me.cobble.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.time.Duration
import java.time.LocalDateTime

class PingCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("ping", "Pong!").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "ping") {
            val botPing = Duration.between(interaction.timeCreated.toLocalDateTime(), LocalDateTime.now())
            interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle("Pong!")
                    .setDescription("The bot is alive!")
                    .addField("API Latency", "${event.jda.gatewayPing}ms", true)
                    .addField("Bot Latency", "${botPing.toMillis()}ms", true)
                    .addField("Shard ID", "${event.jda.shardInfo.shardId}", true)
                    .setColor(Color.GREEN)
                    .build()
            ).queue()
        }
    }
}