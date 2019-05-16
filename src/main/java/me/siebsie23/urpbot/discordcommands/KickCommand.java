package me.siebsie23.urpbot.discordcommands;

import me.siebsie23.urpbot.main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class KickCommand implements MessageCreateListener {

    private Main plugin;

    public KickCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().startsWith("!kick")) {
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
            if(args.length == 2) {
                final User user = event.getMessage().getMentionedUsers().get(0);
                event.getMessage().getServer().get().kickUser(user);
                CompletableFuture<Message> message = event.getMessage().getChannel().sendMessage("User has been kicked!");
                new BukkitRunnable() {
                    public void run() {
                        try {
                            message.get().delete();
                            event.getMessage().delete();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }.runTaskLater(plugin, 100);

            }else {
                event.getChannel().sendMessage(":x: Invalid amount of arguments! :x:");
            }
        }

    }

}
