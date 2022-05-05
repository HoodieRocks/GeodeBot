package me.cobble.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import me.cobble.utilities.StickyUtils

class UnstickCommand(api: DiscordApi, private val prefix: String) : MessageCreateListener {

    init {
        api.addMessageCreateListener(this)
    }

    override fun onMessageCreate(event: MessageCreateEvent?) {
        if (event?.messageContent?.startsWith("${prefix}unstick") == true) {
            val channel: Long = event.channel?.id ?: 0

            if (event.message.author.isRegularUser && event.message.author.canManageMessagesInTextChannel()) {
                StickyUtils.removeStickyMessage(channel)
                event.message.reply(
                    EmbedBuilder()
                        .setTitle("Sticky message removed!")
                        .setDescription("Your sticky message has been removed from the last message in this channel. To stick it again, use `${prefix}stick`")
                )
            }
        }
    }
}