package me.siebsie23.urpbot.discordcommands;

import com.google.common.util.concurrent.FutureCallback;
import me.siebsie23.urpbot.api.DiscordBot;
import me.siebsie23.urpbot.main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ClearCommand implements MessageCreateListener {

    private Main plugin;

    public ClearCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        CompletableFuture<Message> clearing = null;
        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().startsWith("!clear")) {
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
            if (args.length != 2 && event.getMessage().getMentionedUsers().size() == 0
                    || args.length != 3 && event.getMessage().getMentionedUsers().size() == 1) {
                event.getChannel().sendMessage(":x: Invalid amount of arguments! :x:");
                return;
            }
            int amount = -1;
            try {
                amount = Integer.parseInt(args[1]);
                clearing = event.getChannel().sendMessage("Clearing " + amount + " messages...");
            } catch (NumberFormatException ignored) {
                // Ignore exception
            }
            if (amount < 1) {
                event.getChannel().sendMessage(":x: The amount must be greater then 0! :x:");
            }

            final User fromAuthor = args.length == 2 ? null : event.getMessage().getMentionedUsers().get(0);

            CompletableFuture<MessageSet> messages = event.getMessage().getMessagesBefore(amount);

            try {
                for(Message message : messages.get()) {
                    if(fromAuthor != null && message.getAuthor().asUser().get() == fromAuthor) {
                        message.delete();
                    }else if(fromAuthor == null) {
                        message.delete();
                    }
                }
                CompletableFuture<Message> finalClearing = clearing;
                new BukkitRunnable() {
                    public void run() {
                        try {
                            finalClearing.get().delete();
                            event.getMessage().delete();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }.runTaskLater(plugin, 100);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

}
