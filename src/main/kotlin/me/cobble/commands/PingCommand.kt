package me.cobble.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import java.awt.Color

class PingCommand(api: DiscordApi, private val prefix: String) : MessageCreateListener {

    init {
        api.addMessageCreateListener(this)
    }

    override fun onMessageCreate(event: MessageCreateEvent?) {
        if (event?.messageContent?.startsWith("${prefix}ping") == true) {
            event.message.reply(
                EmbedBuilder()
                    .setTitle("Pong!")
                    .setDescription("The bot is alive!")
                    .setColor(Color.GREEN)
            )
        }
    }
}