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

class IdeaCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("idea", "Generates an idea.").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "idea") {
            val url = URL("https://itsthisforthat.com/api.php?json")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            val activity = Json.parseToJsonElement(response.body!!.string()).jsonObject
            val embed = EmbedBuilder()
            embed.setTitle("Idea generator")
                .setColor(Color.CYAN)
                .setDescription("${activity["this"].toString().removeFirstLast()} for ${activity["that"].toString().removeFirstLast()}")
                .setFooter("Idea by DinoBrik")

            event.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }

    // remove first and last letter of string
    private fun String.removeFirstLast(): String {
        return this.substring(1, this.length - 1)
    }
}