package me.cobble

import me.cobble.commands.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Activity

fun main() {
    println("Logging in...")
    val client = JDABuilder.createDefault(System.getenv("BOT_TOKEN"))

    PingCommand(client)
    TodoCommand(client)
    UselessFactCommand(client)
    InviteCommand(client)
    IdeaCommand(client)
    HelpCommand(client)
    InsultMeCommand(client)

    for (int in 0..10) {
        client.useSharding(int, 10)
            .build()
    }


    client.build().presence.setPresence(Activity.listening("slash commands"), false)

    println("Loaded Command Listeners")

    println("Indigo is now running")
}
