package me.cobble

import me.cobble.listeners.MessageSentListener
import me.cobble.commands.*
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.activity.ActivityType
import me.cobble.utilities.PersistenceUtility
import me.cobble.utilities.StickyUtils

const val prefix: String = "$"

fun main() {
    println("Logging in...")
    val client: DiscordApi = DiscordApiBuilder().setToken(System.getenv("BOT_TOKEN")).login().join()

    client.updateActivity(ActivityType.WATCHING, "for $prefix Commands")
    println("Set activity to \"Watching for $prefix Commands\"")

    PingCommand(client)
    StickCommand(client, prefix)
    UnstickCommand(client, prefix)
    IdeaAPICommand(client)
    UselessFactCommand(client)
    HelpCommand(client, prefix)
    InviteCommand(client)
    println("Loaded Command Listeners")

    client.addListener(MessageSentListener())
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
}
