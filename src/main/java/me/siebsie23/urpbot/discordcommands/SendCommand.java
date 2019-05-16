package me.siebsie23.urpbot.discordcommands;

import me.siebsie23.urpbot.main.Main;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.nio.channels.Channel;

public class SendCommand implements MessageCreateListener {

    private Main plugin;

    public SendCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().startsWith("!send")) {
            if(event.getMessage().isPrivateMessage()) {
                event.getMessageAuthor().asUser().get().sendMessage(":x: This command is only available in the MagicalPark discord! :x:");
                return;
            }

            boolean hasPermission = false;
            for (Role role : event.getMessageAuthor().asUser().get().getRoles(event.getServer().get())) {
                if (role.getName().equalsIgnoreCase("Crew") || role.getName().equalsIgnoreCase("Owner")) {
                    hasPermission = true;
                }
            }
            if (!hasPermission) {
                event.getChannel().sendMessage(":x: You do not have the required permissions! :x:");
                return;
            }

            String[] args = event.getMessageContent().split(" ");
            if(args.length >= 2) {
                if(event.getMessage().getMentionedChannels().size() > 0) {
                    final ServerTextChannel channel = event.getMessage().getMentionedChannels().get(0);
                    StringBuilder text = new StringBuilder();
                    for(int i = 2; i < args.length; i++) {
                        text.append(args[i]);
                        text.append(" ");
                    }
                    channel.sendMessage(String.valueOf(text));
                }else {
                    event.getChannel().sendMessage(":x: No channel specified! :x:");
                }
            }else {
                event.getChannel().sendMessage(":x: Invalid amount of arguments! :x:");
            }
        }

    }
}
