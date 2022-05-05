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
import java.text.DecimalFormat

class IdeaAPICommand(api: DiscordApi) : SlashCommandCreateListener {

    init {
        api.addSlashCommandCreateListener(this)
        SlashCommand.with("idea", "Here's something to do!").createGlobal(api).join()
    }

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent?) {
        val interaction = event?.slashCommandInteraction
        if(interaction?.commandName == "idea") {
            val url = URL("https://www.boredapi.com/api/activity")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            val activity = Json.parseToJsonElement(response.body()!!.string()).jsonObject
            val df = DecimalFormat("#.##")

            val embed = EmbedBuilder()
                .setTitle("Here's something to do!")
                .addField("Activity", activity["activity"].toString().replace("\"", ""))
                .addField("Type", activity["type"].toString().replace("\"", ""))
                .addField("Participants", activity["participants"].toString().plus(" Participants").replace("\"", ""))
                .addField("Price", df.format(activity["price"].toString().toFloat() * 100).toString().plus("$"))
                .addField("Accessibility", activity["accessibility"].toString().replace("\"", ""))
                .setDescription("[Click here to learn more](https://www.boredapi.com/)")
                .setColor(Color.CYAN)

            interaction.createImmediateResponder().addEmbed(embed).respond()
        }
    }
}