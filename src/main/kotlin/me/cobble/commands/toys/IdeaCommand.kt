package me.cobble.commands.toys

import me.cobble.utilities.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class IdeaCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("idea", "Generates an idea.").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "idea") {

            val activity = Utils.makeGetRequest("https://itsthisforthat.com/api.php?json")
            val embed = EmbedBuilder()

            embed.setTitle("Idea generator")
                .setColor(Color.CYAN)
                .setDescription(
                    "${activity["this"].toString().removeFirstLast()} for ${
                        activity["that"].toString().removeFirstLast()
                    }"
                )
                .setFooter("Idea by DinoBrik")

            event.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }

    // remove first and last letter of string
    private fun String.removeFirstLast(): String {
        return this.substring(1, this.length - 1)
    }
}