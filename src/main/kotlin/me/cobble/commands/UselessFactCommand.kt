package me.cobble.commands

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.listener.interaction.SlashCommandCreateListener
import java.awt.Color
import java.net.URL

class UselessFactCommand(api: DiscordApi) : SlashCommandCreateListener {

    init {
        api.addSlashCommandCreateListener(this)
        SlashCommand.with("fact", "Get a random fact").createGlobal(api).join()
    }

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent?) {
        val interaction = event?.slashCommandInteraction
        if (interaction?.commandName == "fact") {
            val url = URL("https://uselessfacts.jsph.pl/random.json?language=en")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            val activity = Json.parseToJsonElement(response.body()!!.string()).jsonObject

            val embed = EmbedBuilder()
                .setTitle("Here's a fun fact!")
                .setDescription(activity["text"].toString().replace("\"", ""))
                .addField(
                    "Source",
                    "[${activity["source"].toString().replace("\"", "")}](${
                        activity["source_url"].toString().replace("\"", "")
                    })"
                )
                .setFooter("Powered by https://uselessfacts.jsph.pl/")
                .setColor(Color.CYAN)

            interaction.createImmediateResponder().addEmbed(embed).respond()

        }
    }
}