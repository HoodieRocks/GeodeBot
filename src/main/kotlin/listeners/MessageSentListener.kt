package listeners

import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import utilities.StickyUtils

class MessageSentListener : MessageCreateListener {

    override fun onMessageCreate(message: MessageCreateEvent) {
        if (StickyUtils.getAllStickyMessages().containsKey(message.channel.id) && !message.messageAuthor.isYourself) {
            StickyUtils.getStickyMessage(message.channel.id).let { m ->
                message.channel.sendMessage(message.channel.getMessageById(m).get().content)
                message.channel.getMessages(3).get().first().delete()
            }

        }
    }
}