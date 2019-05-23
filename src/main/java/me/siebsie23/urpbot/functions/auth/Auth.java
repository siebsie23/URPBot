package me.siebsie23.urpbot.functions.auth;

import me.siebsie23.urpbot.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Auth implements Listener {

    private Main plugin;
    public AuthYAML authyml;

    public HashMap<Player, String> code = new HashMap<Player, String>();
    public ArrayList<Player> isRegistering = new ArrayList<>();

    public Auth(Main plugin) {
        this.plugin = plugin;
        authyml = new AuthYAML(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(e.getPlayer().isOp()) {
            authyml.checkPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(code.containsKey(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("[URPBot] You are not allowed to move! Your discord account is not verified yet! Please send the following code to the MagicalPark bot in PM: §7" + code.get(e.getPlayer()));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(code.containsKey(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("[URPBot] You are not allowed to move! Your discord account is not verified yet! Please send the following code to the MagicalPark bot in PM: §7" + code.get(e.getPlayer()));
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(code.containsKey(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("[URPBot] You are not allowed to move! Your discord account is not verified yet! Please send the following code to the MagicalPark bot in PM: §7" + code.get(e.getPlayer()));
        }
    }

    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(code.containsKey(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("[URPBot] You are not allowed to move! Your discord account is not verified yet! Please send the following code to the MagicalPark bot in PM: §7" + code.get(e.getPlayer()));
        }
    }

}
