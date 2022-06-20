package me.cobble.commands.`fun`

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import me.cobble.utilities.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL
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
            var url = URL("https://xkcd.com/info.0.json")
            if (interaction.options.size != 0) {
                url = URL("https://xkcd.com/${interaction.getOption("comic_id")!!.asInt}/info.0.json")
            }
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val json = Json.parseToJsonElement(response.body!!.string()).jsonObject
            val embed = EmbedBuilder()
                .setColor(0xDDFFEE)
                .setTitle(unquote(json["title"].toString()))
                .addField("Number", json["num"].toString(), true)
                .addField(
                    "Date",
                    "${DateFormatSymbols.getInstance().months[unquote(json["month"].toString()).toInt() - 1]} ${unquote(json["day"].toString())}, ${
                        unquote(json["year"].toString())
                    }",
                    true
                )
                .addField("Alt Text", json["alt"].toString(), false)
                .setImage(unquote(json["img"].toString()))
                .setFooter("https://xkcd.com/")
            interaction.replyEmbeds(embed.build()).queue()
        }
    }

    private fun unquote(str: String): String {
        return Utils().stripQuotation(str)
    }
}