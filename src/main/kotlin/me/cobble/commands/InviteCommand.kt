package me.cobble.commands

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class InviteCommand(api: JDA) : ListenerAdapter() {

    init {
        api.addEventListener(this)
        api.upsertCommand("invite", "Invite me to your server!").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "invite") {
            interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle("Invite me to your server!")
                    .setDescription("[Click here](https://discord.com/oauth2/authorize?client_id=963885994468335736&scope=applications.commands&permissions=277025651776)")
                    .build()
            ).complete()
        }
    }
}