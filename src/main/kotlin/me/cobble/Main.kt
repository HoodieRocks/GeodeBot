package me.cobble

import io.github.cdimascio.dotenv.dotenv
import me.cobble.commands.`fun`.*
import me.cobble.commands.utility.*
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

fun main() {
    println("Logging in...")

    val token = if(System.getProperty("os.name").contains("Windows")) dotenv {
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }["BOT_TOKEN"] else System.getenv("BOT_TOKEN")

    val client = JDABuilder.createDefault(token)

    client.addEventListeners(
        PingCommand(client),
        TodoCommand(client),
        UselessFactCommand(client),
        InviteCommand(client),
        IdeaCommand(client),
        EightBallCommand(client),
        InsultMeCommand(client),
        PasswordGenCommand(client),
        TicketCommand(client),
        HelpCommand(client)
    )

    for (int in 0..2) {
        client.useSharding(int, 3)
            .build()
            .awaitReady()
            .presence.setPresence(Activity.listening("slash commands | shard $int"), false)
    }

    println("Loaded Command Listeners")
    println("Indigo is now running")
}
