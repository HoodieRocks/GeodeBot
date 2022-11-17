package me.cobble

import io.github.cdimascio.dotenv.dotenv
import me.cobble.commands.toys.*
import me.cobble.commands.utility.*
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.slf4j.LoggerFactory

fun main() {
    val logger = LoggerFactory.getLogger("AmethystBot")

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
        TicketCommand(client),
        XKCDCommand(client),
        InfoCommand(client),
        HelpCommand(client),
        WordGuessGame(client)
    )

    disableCache(client)

    val jda = client.build()

    jda.presence.setStatus(OnlineStatus.IDLE)
    jda.awaitReady().presence.setPresence(Activity.listening("slash commands"), false)
    jda.presence.setStatus(OnlineStatus.ONLINE)

    logger.info("Loaded Command Listeners")
    logger.info("Indigo is now running")
}

fun disableCache(builder: JDABuilder) {
    builder.setBulkDeleteSplittingEnabled(false)
    builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.VOICE_STATE)
    builder.setLargeThreshold(50)
    builder.setChunkingFilter(ChunkingFilter.NONE)
}
