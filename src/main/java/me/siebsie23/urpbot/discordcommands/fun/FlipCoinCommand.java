package me.siebsie23.urpbot.discordcommands.fun;

import me.siebsie23.urpbot.main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Random;

public class FlipCoinCommand implements MessageCreateListener {

    private Main plugin;

    public FlipCoinCommand(Main plugin) {
        this.plugin = plugin;
    }

    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().equalsIgnoreCase("!flip")) {
            event.getMessage().getChannel().sendMessage(":moneybag: Flipping a coin...");
            new BukkitRunnable() {
                public void run() {
                    event.getMessage().getChannel().sendMessage("I flipped " + flip() + " for you!");
                }
            }.runTaskLater(plugin, 10L);
        }
    }

    private String flip(){
        Random random = new Random();
        int result = random.nextInt(2);
        if(result == 0){
            return "heads";
        }else{
            return "tails";
        }
    }

}
