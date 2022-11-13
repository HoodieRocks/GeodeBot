package me.cobble.commands.toys

import me.cobble.utilities.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class UselessFactCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("fact", "Get a random fact").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "fact") {
            val activity = Utils.makeGetRequest("https://uselessfacts.jsph.pl/random.json?language=en")

            val embed = EmbedBuilder()
                .setTitle("Here's a fun fact!")
                .setDescription(activity["text"].toString().removeFirstAndLastCharacter())
                .addField(
                    "Source",
                    "[${activity["source"].toString().removeFirstAndLastCharacter()}](${
                        activity["source_url"].toString().removeFirstAndLastCharacter()
                    })", false
                )
                .setFooter("Powered by https://uselessfacts.jsph.pl/")
                .setColor(Color.CYAN)

            interaction.replyEmbeds(embed.build()).setEphemeral(true).queue()

        }
    }

    // removes the first and last character of a string
    private fun String.removeFirstAndLastCharacter(): String {
        return this.substring(1, this.length - 1)
    }
}