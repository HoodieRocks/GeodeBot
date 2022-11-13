package me.cobble.commands.toys

import me.cobble.utilities.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import java.awt.Color

class TodoCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("todo", "Here's something to do!")
            .addOption(OptionType.STRING, "type", "Get a random task of a specific type", false).queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "todo") {
            val type: String =
                if (interaction.getOption("type") == null) "" else interaction.getOption("type")!!.asString

            val activity = Utils.makeGetRequest("https://www.boredapi.com/api/activity?type=$type")

            val embed: EmbedBuilder
            if (activity["error"] == null) {
                embed = EmbedBuilder()
                    .setTitle("Here's something to do!")
                    .addField("Activity", activity["activity"].toString().removeFirstLast(), false)
                    .addField("Type", activity["type"].toString().removeFirstLast(), false)
                    .addField("Participants", activity["participants"].toString().plus(" Participants"), false)
                    .addField("Price", (activity["price"].toString().toFloat() * 10).toString().plus("$"), false)
                    .addField("Accessibility", activity["accessibility"].toString(), false)
                    .setDescription("[Click here to learn more](https://www.boredapi.com/)")
                    .setColor(Color.CYAN)
            } else {
                embed = EmbedBuilder()
                    .setTitle("Error")
                    .setDescription(activity["error"].toString().removeFirstLast())
                    .setColor(Color.RED)
            }


            try {
                interaction.replyEmbeds(embed.build()).setEphemeral(true).queue()
            } catch (e: Exception) {
                interaction.reply("Not a valid option")
            }
        }
    }

    // remove first and last letter of string
    private fun String.removeFirstLast(): String {
        return this.substring(1, this.length - 1)
    }
}