package me.cobble.commands.utility

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.interactions.components.buttons.Button

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
                ),
                SubcommandData("setup", "Setups up ticketing").addOption(
                    OptionType.CHANNEL,
                    "channel",
                    "Channel to send message to",
                    true
                )
            ).queue()
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val interaction = event.interaction
        if (interaction.name == "ticket") {

            // CREATION
            if (interaction.subcommandName == "create") {
                openTicket(interaction)
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
                        ).setEphemeral(true).queue()
                        return
                    }
                    return
                } else {
                    val channel = interaction.getOption("channel")!!.asChannel
                    if (channel.name.startsWith("ticket-") && (interaction.member!!.hasPermission(Permission.MANAGE_CHANNEL) || channel.name.contains(
                            interaction.user.name.lowercase()
                        ))
                    ) {
                        channel.delete().queue()
                        interaction.replyEmbeds(
                            EmbedBuilder().setTitle("Tickets").setDescription("Ticket closed!").setColor(0xDF6743)
                                .build()
                        ).setEphemeral(true).queue()
                        return
                    }
                    return
                }
            }

            // SETUP
            if (interaction.subcommandName == "setup") {
                if (interaction.getOption("channel") != null) {
                    val channel = interaction.getOption("channel")!!.asChannel
                    channel.asTextChannel().sendMessageEmbeds(
                        EmbedBuilder()
                            .setTitle("Tickets")
                            .setDescription("Click the button to make a ticket")
                            .build()
                    )
                        .setActionRow(Button.primary("create_ticket", "Create Ticket"))
                        .queue()
                    interaction.reply("Created").setEphemeral(true).queue()
                }
            }
        }
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if (event.button.id == "create_ticket") {
            openTicket(event.interaction)
        }

        if (event.button.id == "close_ticket") {
            val interaction = event.interaction
            val channel = interaction.channel
            if (channel.name.startsWith("ticket-") && (interaction.member!!.hasPermission(Permission.MANAGE_CHANNEL) || channel.name.contains(
                    interaction.user.name.lowercase()
                ))
            ) {
                interaction.channel.delete().queue()
                interaction.replyEmbeds(
                    EmbedBuilder().setTitle("Tickets").setDescription("Ticket closed!").setColor(0xDF6743)
                        .build()
                ).setEphemeral(true).queue()
                return
            }

        }
    }

    private fun openTicket(interaction: IReplyCallback) {
        if (interaction.guild?.getCategoriesByName("tickets", true) == null) {
            interaction.guild?.createCategory("tickets")?.queue()
        }

        val category = interaction.guild?.getCategoriesByName("tickets", true)
        if (!category.isNullOrEmpty()) {
            category.first().let {
                it.textChannels.forEach { ch ->
                    if (ch.name.replace("ticket-", "") == interaction.user.name.lowercase()) {
                        interaction.reply("You already have a ticket open!").setEphemeral(true).queue()
                        return
                    }
                }
                it.createTextChannel("ticket-${interaction.user.name}").setParent(it)
                    .setTopic("Ticket created by ${interaction.user.name}").complete()

                it.textChannels.forEach { ch ->
                    if (ch.name.replace("ticket-", "") == interaction.user.name.lowercase()) {
                        ch.sendMessageEmbeds(
                            EmbedBuilder().setTitle("Tickets").setDescription("Click the button to close this ticket")
                                .build()
                        ).setActionRow(Button.danger("close_ticket", "Close ticket")).queue()
                    }
                }
            }
            interaction.replyEmbeds(
                EmbedBuilder().setColor(0x26DFB2).setTitle("Tickets").setDescription("Ticket Created!").build()
            ).setEphemeral(true).queue()
        }
    }
}