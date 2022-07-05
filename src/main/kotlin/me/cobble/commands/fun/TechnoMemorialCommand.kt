package me.cobble.commands.`fun`

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class TechnoMemorialCommand : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        if (message.contentRaw == "a!potato") {
            event.channel.sendMessageEmbeds(
                EmbedBuilder()
                    .setColor(Color.PINK)
                    .setTitle("Thank you for many years of happiness")
                    .setDescription("Thank you Techno for many years of happiness, you've made the world a better place with your words, actions and attitude. Let you live on within our hearts and minds, forever and ever")
                    .build()
            ).queue()
        }
    }
}