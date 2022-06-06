package me.cobble.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class InviteCommand(private val api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("invite", "Invite me to your server!").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "invite") {
            interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle("Invite me to your server!")
                    .setDescription(
                        "[Click here](${
                            api.build().retrieveApplicationInfo().complete().getInviteUrl(Permission.ADMINISTRATOR)
                                .replace("scope=bot", "scope=applications.commands")
                        }) to invite me to your server!"
                    )
                    .build()
            ).queue()
        }
    }
}