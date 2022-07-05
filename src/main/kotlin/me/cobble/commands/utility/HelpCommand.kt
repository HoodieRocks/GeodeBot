package me.cobble.commands.utility

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.Command
import java.util.*

class HelpCommand(private val api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("help", "List commands and features of Amethyst").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "help") {
            val embed = EmbedBuilder()

            embed.setTitle("All commands")
            val commands = api.build().retrieveCommands().complete()
            Collections.sort(commands, Comparator.comparing(Command::getName))
            commands.forEach {
                embed.addField(it.name, "`${it.description}`", false)
            }
            embed
                .setColor(0xccaed9)
                .setThumbnail("https://cdn.discordapp.com/avatars/963885994468335736/7ce0ad7a9b5cf58861563910ebc81f7d.webp?size=256")
            interaction.replyEmbeds(embed.build()).queue()
        }
    }
}