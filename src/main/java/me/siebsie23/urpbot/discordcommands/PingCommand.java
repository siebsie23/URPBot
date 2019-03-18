package me.siebsie23.urpbot.discordcommands;

import me.siebsie23.urpbot.main.Main;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class PingCommand implements MessageCreateListener {

    private Main plugin;

    public PingCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void onMessageCreate(MessageCreateEvent event) {

        long Time = System.currentTimeMillis();

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().equalsIgnoreCase("!ping")) {

            event.getMessage().getChannel().sendMessage("Pong! " + (System.currentTimeMillis() - Time) + "ms");

        }

    }

}
