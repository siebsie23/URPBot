package me.siebsie23.urpbot.events;

import me.siebsie23.urpbot.api.DiscordBot;
import me.siebsie23.urpbot.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class ChatLog implements Listener, MessageCreateListener {

    private Main plugin;
    private String channelId = "579216274588958730";

    public ChatLog(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        ServerTextChannel channel = DiscordBot.getInstance().getChannelById(channelId).get().asServerTextChannel().get();
        channel.sendMessage("**" + e.getPlayer().getDisplayName() + ":** " + e.getMessage());
    }

    public void onMessageCreate(MessageCreateEvent e) {
        if(!e.getMessageAuthor().isUser())
            return;
        if(e.getMessage().getChannel().getIdAsString().equalsIgnoreCase(channelId)) {
            if(!e.getMessage().getContent().startsWith("**")) {
                Bukkit.broadcastMessage("§7[§5Discord§7] §o" + e.getMessageAuthor().getDisplayName() + "§7: §r" + e.getMessage().getContent());
            }
        }
    }

}
