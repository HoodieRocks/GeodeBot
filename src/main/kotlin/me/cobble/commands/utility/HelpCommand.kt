package me.cobble.commands.utility

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class HelpCommand(private val api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("help", "List commands and features of Amethyst").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "help") {
            val embed = EmbedBuilder()

            embed.setTitle("All commands")
            api.build().retrieveCommands().complete().forEach {
                embed.addField(it.name, "`${it.description}`", false)
            }
            embed.setColor(Color.MAGENTA)
            interaction.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }
}