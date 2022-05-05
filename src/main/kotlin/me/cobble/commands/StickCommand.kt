package me.cobble.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import me.cobble.utilities.StickyUtils
import java.awt.Color

class StickCommand(api: DiscordApi, private val prefix: String) : MessageCreateListener {

    init {
        api.addMessageCreateListener(this)
    }

    override fun onMessageCreate(event: MessageCreateEvent?) {
        if (event?.messageContent?.startsWith("${prefix}stick") == true) {
            val id: Long = event.channel.getMessages(2).get().first().id
            val channel: Long = event.channel?.id ?: 0

            if (event.message.author.isRegularUser && event.message.author.canManageMessagesInTextChannel()) {

                StickyUtils.setStickyMessage(channel, id)
                event.message.reply(
                    EmbedBuilder()
                        .setTitle("Sticky message set!")
                        .setDescription("Your sticky message has been set to the last message in this channel. To unstick it, use `${prefix}unstick`")
                        .setColor(Color.CYAN)
                ).thenAccept {
                    event.message.delete()
                }
            }
        }
    }
}