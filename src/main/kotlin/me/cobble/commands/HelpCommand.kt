package me.cobble.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.listener.interaction.SlashCommandCreateListener
import java.awt.Color

class HelpCommand(api: DiscordApi, private val prefix: String) : SlashCommandCreateListener {

    init {
        api.addSlashCommandCreateListener(this)
        SlashCommand.with("help", "List commands and features of Amethyst").createGlobal(api).join()
    }

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent?) {
        val interaction = event?.slashCommandInteraction
        if (interaction?.commandName == "help") {
            val embed = EmbedBuilder()
                .setTitle("All commands")
                .setDescription(
                    "`${prefix}help` - Shows this message\n" +
                            "`/fact` - Shows a random fact\n" +
                            "`/idea` - Shows a random idea\n" +
                            "`${prefix}stick` - Sets a sticky message to last message\n" +
                            "`${prefix}unstick` - Removes the sticky message\n" +
                            "`/ping` - Pong!\n" +
                            "`/invite` - Invite the bot to your server\n"
                )

                .setColor(Color.BLUE)

            interaction.createImmediateResponder().addEmbed(embed).respond()
        }
    }
}