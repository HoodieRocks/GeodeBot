import commands.*
import listeners.MessageSentListener
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.activity.ActivityType
import utilities.PersistenceUtility
import utilities.StickyUtils

const val prefix: String = "$"

fun main() {
    println("Logging in...")
    val client: DiscordApi = DiscordApiBuilder().setToken(readToken()).login().join()

    client.updateActivity(ActivityType.WATCHING, "for $prefix Commands")
    println("Set activity to \"Watching for $prefix Commands\"")

    PingCommand(client, prefix)
    StickCommand(client, prefix)
    UnstickCommand(client, prefix)
    IdeaAPICommand(client, prefix)
    UselessFactCommand(client, prefix)
    HelpCommand(client, prefix)
    InviteCommand(client, prefix)
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

fun readToken(): String {
    return {}::class.java.getResource("token.txt")?.readText() ?: ""
}
