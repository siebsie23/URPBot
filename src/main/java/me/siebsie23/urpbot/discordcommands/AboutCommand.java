package me.siebsie23.urpbot.discordcommands;

import me.siebsie23.urpbot.main.Main;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class AboutCommand implements MessageCreateListener {

    private Main plugin;

    private final long startTime = System.currentTimeMillis();

    public AboutCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if(!event.getMessageAuthor().isUser())
            return;

        if(event.getMessageContent().equalsIgnoreCase("!about") || event.getMessageContent().equalsIgnoreCase("!version")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.MAGENTA)
                    .setTitle("MagicalPark Discord Bot")
                    .setThumbnail("https://pbs.twimg.com/profile_images/969947770919546880/f0v5ebUT_400x400.jpg")
                    .setDescription(
                            "Some information about the MagicalPark Discord Bot!"
                    )
                    .addField("Developed by:", "siebsie23")
                    .addField("Version:", plugin.getDescription().getVersion())
                    .addField("Uptime:", getUptime())
                    .addField("Help Command:", "!help");
            event.getChannel().sendMessage(embed);
        }

    }

    private String getUptime() {
        long millis = System.currentTimeMillis() - startTime;
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        return days + " Days " + hours + " Hours " + minutes + " Minutes " + seconds + " Seconds";
    }

}
