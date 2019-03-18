package me.siebsie23.urpbot.main;

import me.siebsie23.urpbot.api.DiscordBot;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private DiscordBot discordBot = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String token = getConfig().getString("token");
        discordBot = new DiscordBot(this, token);
    }

    @Override
    public void onDisable() {
        if(discordBot != null) {
            discordBot.shutdown();
        }
    }

}
