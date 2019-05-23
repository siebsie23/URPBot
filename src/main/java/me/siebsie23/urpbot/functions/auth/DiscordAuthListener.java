package me.siebsie23.urpbot.functions.auth;

import me.siebsie23.urpbot.main.Main;
import org.bukkit.entity.Player;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Map;

public class DiscordAuthListener implements MessageCreateListener {

    private Main plugin;

    public DiscordAuthListener(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessage().isPrivateMessage()) {
            if(plugin.auth.code.containsValue(event.getMessageContent())) {
                Player player = getKey(plugin.auth.code, event.getMessageContent());
                assert player != null;
                if(plugin.auth.isRegistering.contains(player)) {
                    plugin.auth.authyml.createIpFile(player, event.getMessageAuthor().asUser().get());
                    event.getMessage().getChannel().sendMessage("Your account has been registered successfully!");
                    return;
                }
                event.getMessage().getChannel().sendMessage("Your account has been verified successfully!");
                plugin.auth.code.remove(player);
                player.sendMessage("[URPBot] Your account has been registered successfully!");
            }
        }

    }

    private <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

}
