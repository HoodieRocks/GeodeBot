package me.cobble.commands

import me.cobble.utilities.Phrases
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class IdeaGeneratorCommand(api: JDA) : ListenerAdapter() {

    init {
        api.addEventListener(this)
        api.upsertCommand("idea", "Generates an idea.").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if(interaction.name == "idea") {
            interaction.replyEmbeds(EmbedBuilder()
                .setTitle("Idea generator")
                .addField("Idea", "Your idea is: ${Phrases.getPhrase()}", false)
                .setColor(0x42CCAA)
                .setFooter("Idea generator, suggested by DinoBrik")
                .build()).complete()
        }
    }
}