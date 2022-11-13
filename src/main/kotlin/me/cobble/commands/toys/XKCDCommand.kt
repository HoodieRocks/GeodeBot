package me.cobble.commands.toys

import me.cobble.utilities.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import java.text.DateFormatSymbols

class XKCDCommand(api: JDABuilder) : ListenerAdapter() {
    init {
        api.build().upsertCommand("xkcd", "Get an xkcd comic")
            .addOption(OptionType.INTEGER, "comic_id", "Number of the comic to get", false)
            .queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "xkcd") {
            var url = "https://xkcd.com/info.0.json"
            if (interaction.options.size != 0) {
                url = "https://xkcd.com/${interaction.getOption("comic_id")!!.asInt}/info.0.json"
            }

            val json = Utils.makeGetRequest(url)
            val embed = EmbedBuilder()
                .setColor(0xDDFFEE)
                .setTitle(unquote(json["title"].toString()))
                .addField("Number", json["num"].toString(), true)
                .addField(
                    "Date",
                    "${DateFormatSymbols.getInstance().months[unquote(json["month"].toString()).toInt() - 1]} ${
                        unquote(
                            json["day"].toString()
                        )
                    }, ${
                        unquote(json["year"].toString())
                    }",
                    true
                )
                .addField("Alt Text", json["alt"].toString(), false)
                .setImage(unquote(json["img"].toString()))
                .setFooter("https://xkcd.com/")
            interaction.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }

    private fun unquote(str: String): String {
        return Utils.stripQuotation(str)
    }
}