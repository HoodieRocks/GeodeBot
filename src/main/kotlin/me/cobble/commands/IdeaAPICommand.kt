package me.cobble.commands

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.interaction.SlashCommandOption
import org.javacord.api.interaction.SlashCommandOptionType
import org.javacord.api.listener.interaction.SlashCommandCreateListener
import java.awt.Color
import java.net.URL
import java.text.DecimalFormat

class IdeaAPICommand(api: DiscordApi) : SlashCommandCreateListener {

    init {
        api.addSlashCommandCreateListener(this)
        SlashCommand.with("idea", "Here's something to do!",
            listOf(SlashCommandOption.create(SlashCommandOptionType.STRING, "type", "Type of activity, see https://www.boredapi.com/documentation#endpoints-accessibility-range",
                false))).createGlobal(api).join()
    }

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent?) {
        val interaction = event?.slashCommandInteraction
        if (interaction?.commandName == "idea") {
            val type: String = interaction.getOptionStringValueByIndex(0).orElse("")
            val url = URL("https://www.boredapi.com/api/activity?type=$type")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            val activity = Json.parseToJsonElement(response.body()!!.string()).jsonObject

            val embed = EmbedBuilder()
                .setTitle("Here's something to do!")
                .addField("Activity", activity["activity"].toString().removeFirstLast())
                .addField("Type", activity["type"].toString().removeFirstLast())
                .addField("Participants", activity["participants"].toString().plus(" Participants").removeFirstLast())
                .addField("Price", (activity["price"].toString().toFloat() * 10).toString().plus("$"))
                .addField("Accessibility", activity["accessibility"].toString().removeFirstLast())
                .setDescription("[Click here to learn more](https://www.boredapi.com/)")
                .setColor(Color.CYAN)

            interaction.createImmediateResponder().addEmbed(embed).respond().exceptionally {
                interaction.createImmediateResponder().setContent("Not a valid option").respond().join()
            }
        }
    }

    // remove first and last letter of string
    private fun String.removeFirstLast(): String {
        return this.substring(1, this.length - 1)
    }
}