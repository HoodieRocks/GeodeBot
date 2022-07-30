package me.cobble.listeners

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.time.Duration

class LinkPostListener : ListenerAdapter() {

    private val regex = Regex("(discord\\.gg/[A-z])|(dis\\.gg/[A-z])")

    override fun onMessageReceived(event: MessageReceivedEvent) {
        println("Test")
        val msg = event.message
        val member = event.member!!

        if(regex.containsMatchIn(msg.contentRaw) && (!member.isOwner && !member.hasPermission(Permission.MANAGE_SERVER))) {
            msg.delete().queue()
            event.member!!.timeoutFor(Duration.ofMinutes(1))
        }
    }
}