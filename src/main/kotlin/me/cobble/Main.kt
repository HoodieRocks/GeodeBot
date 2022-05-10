package me.cobble

import me.cobble.commands.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Activity

fun main() {
    println("Logging in...")
    val client: JDA = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build()

    client.presence.setPresence(Activity.listening("slash commands"), false)

    PingCommand(client)
    TodoCommand(client)
    UselessFactCommand(client)
    InviteCommand(client)
    IdeaCommand(client)
    HelpCommand(client)
    println("Loaded Command Listeners")

    println("Indigo is now running")
    println("${client.retrieveApplicationInfo().complete().getInviteUrl(Permission.ADMINISTRATOR)} invite link")
}
