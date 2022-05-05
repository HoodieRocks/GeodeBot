package me.cobble.commands

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import java.awt.Color
import java.net.URL

class UselessFactCommand(api: DiscordApi, private val prefix: String) : MessageCreateListener {

    init {
        api.addMessageCreateListener(this)
    }

    override fun onMessageCreate(event: MessageCreateEvent?) {
        if (event?.message?.content?.startsWith("${prefix}fact") == true) {
            val url = URL("https://uselessfacts.jsph.pl/random.json?language=en")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            event.message.channel.type()

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

            event.message.channel.sendMessage(embed)

        }
    }
}