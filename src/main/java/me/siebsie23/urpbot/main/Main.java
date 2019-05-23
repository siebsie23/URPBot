package me.siebsie23.urpbot.main;

import me.siebsie23.urpbot.api.DiscordBot;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private DiscordBot discordBot = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String token;
        if(getConfig().getBoolean("devmode")) {
            getServer().getLogger().info("Starting URPBot in development mode...");
            token = "NTgxMDE4ODM2MDM2NDg1MTMw.XOZKIg.8_yfmmzOEsBAFEwLXEejtQR5aXo";
        }else {
            token = getConfig().getString("token");
        }
        discordBot = new DiscordBot(this, token);
    }

    @Override
    public void onDisable() {
        if(discordBot != null) {
            discordBot.shutdown();
        }
    }

}
