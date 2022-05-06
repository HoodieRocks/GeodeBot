package me.cobble.listeners

import me.cobble.utilities.StickyUtils
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageSentListener : ListenerAdapter() {

    override fun onMessageReceived(message: MessageReceivedEvent) {
        if (StickyUtils.getAllStickyMessages().containsKey(message.channel.id) && !message.author.isBot) {
            StickyUtils.getStickyMessage(message.channel.id).let { m ->
                message.channel.sendMessage(message.channel.history.getMessageById(m)!!.contentDisplay)
                message.channel.history.retrievePast(3).complete()[0].delete().complete()
            }

        }
    }
}