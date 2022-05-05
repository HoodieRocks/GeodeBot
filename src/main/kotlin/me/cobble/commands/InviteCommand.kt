package me.cobble.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener

class InviteCommand(private val api: DiscordApi, private val prefix: String) : MessageCreateListener {

    init {
        api.addMessageCreateListener(this)
    }

    override fun onMessageCreate(event: MessageCreateEvent?) {
        if(event?.message?.content?.startsWith("${prefix}invite") == true) {
            event.message.channel.sendMessage(EmbedBuilder()
                .setTitle("Invite me to your server!")
                .setDescription("[Click here](${api.createBotInvite()})"))
        }
    }
}