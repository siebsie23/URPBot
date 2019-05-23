package me.siebsie23.urpbot.api;

import me.siebsie23.urpbot.discordcommands.*;
import me.siebsie23.urpbot.discordcommands.fun.FlipCoinCommand;
import me.siebsie23.urpbot.discordcommands.fun.RockPaperScissorsCommand;
import me.siebsie23.urpbot.events.ChatLog;
import me.siebsie23.urpbot.functions.auth.DiscordAuthListener;
import me.siebsie23.urpbot.main.Main;
import org.bukkit.Bukkit;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.logging.Level;

public class DiscordBot {

    private static DiscordApi api;

    public DiscordBot(Main plugin, String token) {

        try {
            api = new DiscordApiBuilder().setToken(token).login().join();

            Bukkit.getLogger().info("Connected to discord account: " + api.getYourself() + ". Setting up listeners...");

            // Listeners
            api.addListener(new HelpCommand(plugin));
            api.addListener(new AboutCommand(plugin));
            api.addListener(new TPSCommand(plugin));
            api.addListener(new ClearCommand(plugin));
            api.addListener(new PingCommand(plugin));
            api.addListener(new KickCommand(plugin));
            api.addListener(new SendCommand(plugin));
            api.addListener(new ChatLog(plugin));
            api.addListener(new FlipCoinCommand(plugin));
            api.addListener(new RockPaperScissorsCommand(plugin));
            api.addListener(new DiscordAuthListener(plugin));
        }catch(Exception e) {
            plugin.getLogger().log(Level.INFO, "Failed to start URPBot! Token may be incorrect.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        plugin.getLogger().log(Level.INFO, "URPBot has been started!");

    }

    public void shutdown() {

        try {
            Runnable runnable = () -> api.disconnect();

            Thread thread = new Thread(runnable);
            thread.start();

            //Sleep for 2s on main thread so disconnect can finish
            Thread.sleep(2000);
        }catch(Exception ignore) {
            //Ignore exception
        }

    }

    public static DiscordApi getInstance() {
        return api;
    }

}
