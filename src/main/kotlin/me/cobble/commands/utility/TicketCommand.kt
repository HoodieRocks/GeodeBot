package me.cobble.commands.utility

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

class TicketCommand(client: JDABuilder) : ListenerAdapter() {

    init {
        client.build().upsertCommand("ticket", "Base command for ticketing")
            .addSubcommands(
                SubcommandData("create", "Creates a ticket"),
                SubcommandData("close", "Closes a ticket").addOption(
                    OptionType.CHANNEL,
                    "channel",
                    "The channel to close",
                    false
                )
            ).queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "ticket") {

            // CREATION
            if (interaction.subcommandName == "create") {
                if (interaction.guild?.getCategoriesByName("tickets", true) == null) {
                    interaction.guild?.createCategory("tickets")?.queue()
                }

                val category = interaction.guild?.getCategoriesByName("tickets", true)
                if (category != null && category.isNotEmpty()) {
                    category.first().let {
                        it.textChannels.forEach { ch ->
                            if (ch.name.replace("ticket-", "") == interaction.user.name.lowercase()) {
                                interaction.reply("You already have a ticket open!").queue()
                                return
                            }
                        }
                        it.createTextChannel("ticket-${interaction.user.name}").setParent(it)
                            .setTopic("Ticket created by ${interaction.user.name}").queue()
                    }
                    interaction.replyEmbeds(
                        EmbedBuilder().setColor(0x26DFB2).setTitle("Tickets").setDescription("Ticket Created!").build()
                    ).queue()
                } else {
                    interaction.reply("Setting up a channel category... Rerun command to make a ticket!").queue()
                }
                return
            }

            // CLOSING
            if (interaction.subcommandName == "close") {
                if (interaction.getOption("channel") == null) {
                    val channel = interaction.channel
                    if (channel.name.startsWith("ticket-") && (interaction.member!!.hasPermission(Permission.MANAGE_CHANNEL) || channel.name.contains(
                            interaction.user.name.lowercase()
                        ))
                    ) {
                        interaction.channel.delete().queue()
                        interaction.replyEmbeds(
                            EmbedBuilder().setTitle("Tickets").setDescription("Ticket closed!").setColor(0xDF6743)
                                .build()
                        ).queue()
                        return
                    }
                    return
                } else {
                    val channel = interaction.getOption("channel")!!.asTextChannel
                    if (channel!!.name.startsWith("ticket-") && (interaction.member!!.hasPermission(Permission.MANAGE_CHANNEL) || channel.name.contains(
                            interaction.user.name.lowercase()
                        ))
                    ) {
                        channel.delete().queue()
                        interaction.replyEmbeds(
                            EmbedBuilder().setTitle("Tickets").setDescription("Ticket closed!").setColor(0xDF6743)
                                .build()
                        ).queue()
                        return
                    }
                    return
                }
            }
        }
    }
}