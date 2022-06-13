package me.cobble.commands.`fun`

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class InsultMeCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("insult", "**WARNING: Offensive language**\nInsults the user who invoked the command")
            .queue()
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
            val embed = EmbedBuilder()
            embed.setTitle("Warning: Offensive Language")
                .setColor(0xFF0000)
                .setDescription("||${activity["insult"].toString()}||")
                .addField("Created on", activity["created"].toString().removeFirstLast(), true)
                .addField("Times shown", activity["shown"].toString().removeFirstLast(), true)
            if (activity["comment"].toString().isNotEmpty()) {
                embed.addField("Comment", activity["comment"].toString().removeFirstLast(), true)
            }
            event.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }


    // remove first and last letter of string
    private fun String.removeFirstLast(): String {
        return this.substring(1, this.length - 1)
    }
}