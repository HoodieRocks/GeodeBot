package me.cobble.commands

import org.javacord.api.DiscordApi
import org.javacord.api.entity.message.embed.EmbedBuilder
import org.javacord.api.event.interaction.SlashCommandCreateEvent
import org.javacord.api.interaction.SlashCommand
import org.javacord.api.listener.interaction.SlashCommandCreateListener

class InviteCommand(private val api: DiscordApi) : SlashCommandCreateListener {

    init {
        api.addSlashCommandCreateListener(this)
        SlashCommand.with("invite", "Invite me to your server!").createGlobal(api).join()
    }

    override fun onSlashCommandCreate(event: SlashCommandCreateEvent?) {
        val interaction = event?.slashCommandInteraction
        if(interaction?.commandName == "invite") {
            interaction.createImmediateResponder().addEmbed(EmbedBuilder()
                .setTitle("Invite me to your server!")
                .setDescription("[Click here](https://discord.com/oauth2/authorize?client_id=963885994468335736&scope=applications.commands&permissions=277025651776)")).respond()
        }
    }
}