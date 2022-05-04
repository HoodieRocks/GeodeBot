package commands

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
import java.text.DecimalFormat

class IdeaAPICommand(api: DiscordApi, private val prefix: String) : MessageCreateListener {

    init {
        api.addMessageCreateListener(this)
    }

    override fun onMessageCreate(event: MessageCreateEvent?) {
        if (event?.message?.content?.startsWith("${prefix}idea") == true) {
            val url = URL("https://www.boredapi.com/api/activity")
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            event.message.channel.type()

            val response = client.newCall(request).execute()
            val activity = Json.parseToJsonElement(response.body!!.string()).jsonObject
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

            event.message.channel.sendMessage(embed)

        }
    }
}