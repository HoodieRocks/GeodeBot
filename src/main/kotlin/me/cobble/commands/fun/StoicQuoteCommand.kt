package me.cobble.commands.`fun`

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.awt.Color
import java.net.URL

class StoicQuoteCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("stoic", "Gives you a stoic quote").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if(interaction.name == "stoic") {
            val url = URL("https://api.themotivate365.com/stoic-quote/")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            val quote = Json.parseToJsonElement(response.body!!.string()).jsonObject["data"]!!.jsonObject
            val embed = EmbedBuilder()
                .setTitle("\"Woah, that's deep!\"")
                .setColor(Color.CYAN)
                .setDescription(quote["quote"].toString().removeFirstLast())
                .addField("Author", quote["author"].toString().removeFirstLast(), true)

            event.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }

    // remove first and last letter of string
    private fun String.removeFirstLast(): String {
        return this.substring(1, this.length - 1)
    }
}