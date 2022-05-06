package me.cobble

import me.cobble.listeners.MessageSentListener
import me.cobble.commands.*
import me.cobble.utilities.PersistenceUtility
import me.cobble.utilities.StickyUtils
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

const val prefix: String = "$"

fun main() {
    println("Logging in...")
    val client: JDA = JDABuilder.createDefault(System.getenv("BOT_TOKEN")).build()

    client.presence.setPresence(Activity.listening("For commands"), false)

    PingCommand(client)
    IdeaAPICommand(client)
    UselessFactCommand(client)
    HelpCommand(client, prefix)
    InviteCommand(client)
    IdeaGeneratorCommand(client)
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
}
