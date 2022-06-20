package me.cobble.commands.`fun`

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EightBallCommand(api: JDABuilder) : ListenerAdapter() {

    init {
        api.build().upsertCommand("8ball", "Shake the ball and see your fortune!").queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "8ball") {
            interaction.replyEmbeds(
                EmbedBuilder()
                    .setTitle(":8ball: Eight Ball")
                    .addField("Your prediction", shake(), false)
                    .setColor(0xF3B32D)
                    .build()
            ).queue()
        }
    }

    private fun shake(): String {
        val options = listOf(
            "Hazy",
            "Try Again.",
            "Ask Again Later.",
            "Better Not Tell You Now.",
            "Cannot Predict Now.",
            "Concentrate and Ask Again.",
            "Don't Count on It.",
            "My Reply is No.",
            "My Sources Say No.",
            "Outlook Not So Good.",
            "Very Doubtful.",
            "Yes.",
            "Signs Point to Yes."
        )
        return options.random()
    }
}