package me.siebsie23.urpbot.main;

import me.siebsie23.urpbot.api.DiscordBot;
import me.siebsie23.urpbot.bankutils.BankYML;
import me.siebsie23.urpbot.events.ChatLog;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private DiscordBot discordBot = null;
    public BankYML bankYML = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String token = getConfig().getString("token");
        discordBot = new DiscordBot(this, token);
        bankYML = new BankYML();
        getServer().getPluginManager().registerEvents(new ChatLog(this), this);
    }

    @Override
    public void onDisable() {
        if(discordBot != null) {
            discordBot.shutdown();
        }
    }

}
