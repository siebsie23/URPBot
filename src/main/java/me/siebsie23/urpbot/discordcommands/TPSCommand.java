package me.siebsie23.urpbot.discordcommands;

import me.siebsie23.urpbot.main.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;

public class TPSCommand implements MessageCreateListener {

    private float TPS = 20;
    private long difference;

    public TPSCommand(Main plugin) {

        new BukkitRunnable() {
            private long milli = System.currentTimeMillis();

            @Override
            public void run() {
                difference = System.currentTimeMillis() - milli;
                TPS = (1000 - (difference - 1000)) / 50f;
                milli = System.currentTimeMillis();
            }
        }.runTaskTimer(plugin, 0, 20);

    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().equalsIgnoreCase("!tps")) {

            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.MAGENTA)
                    .setTitle("Server TPS")
                    .setThumbnail("https://pbs.twimg.com/profile_images/969947770919546880/f0v5ebUT_400x400.jpg")
                    .setDescription("I wonder why you want to know... But here you go!")
                    .addField("Ticks Per Second:", TPS + " TPS")
                    .addField("Ms Between 20 Ticks:", difference + " ms")
                    .addField("Server Clock %:", TPS*100/20 + "%");

            event.getChannel().sendMessage(embed);
        }

    }

}
