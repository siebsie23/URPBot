package me.siebsie23.urpbot.discordcommands;

import me.siebsie23.urpbot.main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HelpCommand implements MessageCreateListener {

    private Main plugin;

    public HelpCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().equalsIgnoreCase("!help")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("MagicalPark Discord Bot - help")
                    .setThumbnail("https://pbs.twimg.com/profile_images/969947770919546880/f0v5ebUT_400x400.jpg")
                    .setColor(Color.MAGENTA)
                    .setDescription("MagicalPark Discord Bot - Commands.")
                    .addField("!help", "Shows the help")
                    .addField("!about", "Shows information about the bot (!version also works)");
            event.getMessageAuthor().asUser().get().sendMessage(embed);
            CompletableFuture<Message> pmmsg = event.getMessage().getChannel().sendMessage("Check your PM " + event.getMessageAuthor().asUser().get().getNicknameMentionTag());
            new BukkitRunnable() {
                public void run() {
                    try {
                        pmmsg.get().delete();
                        event.getMessage().delete();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskLater(plugin, 60);
        }

    }

}
