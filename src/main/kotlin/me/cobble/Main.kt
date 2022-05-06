package me.cobble

import me.cobble.listeners.MessageSentListener
import me.cobble.commands.*
import me.cobble.utilities.PersistenceUtility
import me.cobble.utilities.StickyUtils
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Activity

fun main() {
    println("Logging in...")
    val client: JDA = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build()

    client.presence.setPresence(Activity.listening("slash commands"), false)

    PingCommand(client)
    IdeaAPICommand(client)
    UselessFactCommand(client)
    InviteCommand(client)
    IdeaGeneratorCommand(client)
    HelpCommand(client)
    println("Loaded Command Listeners")

    client.addEventListener(MessageSentListener())
    println("Loaded misc Listeners")

    Runtime.getRuntime().addShutdownHook(Thread()
    {
        println("Shutting down...")
        PersistenceUtility.save(StickyUtils.getAllStickyMessages())
        println("Logged out")
    })
    println("Initializing Runtime Hooks")

    StickyUtils.setAllStickyMessages(PersistenceUtility.load())

    println("Indigo is now running")
    println("${client.retrieveApplicationInfo().complete().getInviteUrl(Permission.ADMINISTRATOR)} invite link")
}
