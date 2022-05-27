package me.cobble.commands

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.Color
import java.net.URL

class UselessFactCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("fact", "Get a random fact").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "fact") {
            val url = URL("https://uselessfacts.jsph.pl/random.json?language=en")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            val activity = Json.parseToJsonElement(response.body!!.string()).jsonObject

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

            interaction.replyEmbeds(embed.build()).queue()

        }
    }

    // removes the first and last character of a string
    private fun String.removeFirstAndLastCharacter(): String {
        return this.substring(1, this.length - 1)
    }
}