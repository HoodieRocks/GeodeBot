package commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import java.awt.Color

class HelpCommand(api: DiscordApi, private val prefix: String) : MessageCreateListener {

    init {
        api.addMessageCreateListener(this)
    }

    override fun onMessageCreate(event: MessageCreateEvent?) {
        if (event?.message?.content?.startsWith("${prefix}help") == true) {

            val embed = EmbedBuilder()
                .setTitle("All commands")
                .setDescription("`${prefix}help` - Shows this message\n" +
                        "`${prefix}fact` - Shows a random fact\n" +
                        "`${prefix}idea` - Shows a random idea\n" +
                        "`${prefix}stick` - Sets a sticky message to last message\n" +
                        "`${prefix}unstick` - Removes the sticky message\n" +
                        "`${prefix}ping` - Pong!\n" + "`${prefix}invite` - Invite the bot to your server\n")

                .setColor(Color.BLUE)

            event.message.channel.sendMessage(embed)

        }
    }
}