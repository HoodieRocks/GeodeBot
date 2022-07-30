package me.cobble

import io.github.cdimascio.dotenv.dotenv
import me.cobble.commands.`fun`.*
import me.cobble.commands.utility.*
import me.cobble.listeners.LinkPostListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.cache.CacheFlag

fun main() {
    println("Logging in...")

    val token = if (System.getProperty("os.name").contains("Windows")) dotenv {
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
        XKCDCommand(client),
        InfoCommand(client),
        TechnoMemorialCommand(),
        HelpCommand(client),
        // Listeners
        LinkPostListener()
    )

    disableCache(client)

    for (int in 0..2) {
        val shard = client.useSharding(int, 3).build()
        shard.presence.setStatus(OnlineStatus.IDLE)
        shard.awaitReady().presence.setPresence(Activity.listening("slash commands | shard ${int + 1}"), false)
        shard.presence.setStatus(OnlineStatus.ONLINE)
    }

    println("Loaded Command Listeners")
    println("Indigo is now running")
}

fun disableCache(builder: JDABuilder) {
    builder.setBulkDeleteSplittingEnabled(false)
    builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.VOICE_STATE)
    builder.setLargeThreshold(50)
    builder.setChunkingFilter(ChunkingFilter.NONE)
}
