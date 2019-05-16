package me.siebsie23.urpbot.discordcommands;

import me.siebsie23.urpbot.main.Main;
import org.javacord.api.event.message.MessageCreateEvent;

public class BankCommand {

    private Main plugin;

    public BankCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().startsWith("!bank")) {
            if(event.getMessage().isPrivateMessage()) {
                event.getMessageAuthor().asUser().get().sendMessage(":x: This command is only available in the MagicalPark discord! :x:");
                return;
            }

            String[] args = event.getMessageContent().split(" ");
            if(args.length < 2) {
                // Check of gebruiker een bank account heeft
            }
        }

    }

}
