package me.cobble.commands

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class InsultMeCommand(api: JDA) : ListenerAdapter() {

    init {
        api.addEventListener(this)
        api.upsertCommand("insult", "**WARNING: Offensive language**, Insults the user who invoked the command").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "insult") {
            val url = URL("https://evilinsult.com/generate_insult.php?lang=en&type=json")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            val activity = Json.parseToJsonElement(response.body!!.string()).jsonObject
            val embed: EmbedBuilder = EmbedBuilder()
            embed.setTitle(activity["insult"].toString())
                .setColor(0xFF0000)
                .addField("Created on", activity["created"].toString(), true)
                .addField("Times shown", activity["shown"].toString(), true)
                .addField("Comment", activity["comment"].toString(), true)
            event.replyEmbeds(embed.build()).queue()
        }
    }
}