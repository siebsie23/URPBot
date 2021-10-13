package me.siebsie23.urpbot.main;

import me.siebsie23.urpbot.api.DiscordBot;
import me.siebsie23.urpbot.functions.auth.Auth;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private DiscordBot discordBot = null;
    public Auth auth;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        auth = new Auth(this);
        String token;
        if(getConfig().getBoolean("devmode")) {
            getServer().getLogger().info("Starting URPBot in development mode...");
            // Token is no longer active
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
