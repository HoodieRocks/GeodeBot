package me.cobble.commands.utility

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class InfoCommand(private val api: JDABuilder) : ListenerAdapter() {
    init {
        api.build().upsertCommand("info", "Gets info about bot and creator").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.interaction.name == "info") {
            event.interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle("Info")
                    .setDescription("Bot created and maintained by `Cobblestone#3544`")
                    .setThumbnail("https://cdn.discordapp.com/avatars/963885994468335736/7ce0ad7a9b5cf58861563910ebc81f7d.webp?size=256")
                    .setColor(0xccaed9)
                    .addField("GitHub", "[Click Here](https://github.com/1ndiigo/)", true)
                    .addField("Discord/Support", "[Click Here](https://discord.gg/3znQkj837T)", true)
                    .addField(
                        "Invite", "[Click Here](${
                            api.build().retrieveApplicationInfo().complete().getInviteUrl(
                                Permission.ADMINISTRATOR
                            )
                                .replace("scope=bot", "scope=applications.commands")
                        })", true
                    )
                    .addField("Ping", "/ping", true)
                    .build()
            ).setEphemeral(true).queue()
        }
    }
}