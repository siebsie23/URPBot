package me.siebsie23.urpbot.functions.auth;

import me.siebsie23.urpbot.utils.PlayerIP;
import me.siebsie23.urpbot.utils.RandomCode;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.javacord.api.entity.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AuthYAML {

    private Auth auth;

    public AuthYAML(Auth auth) {
        this.auth = auth;
    }

    public void checkPlayer(Player player) {
        File file = new File("plugins/URPBot/auth/" + player.getUniqueId() + ".yml");
        if(file.exists()) {
            compareIp(player);
        }else {
            String code = RandomCode.generateCode(5);
            auth.code.put(player, code);
            auth.isRegistering.add(player);
            player.sendMessage("[URPBot] You are not allowed to move! Your discord account is not linked yet! Please send the following code to the MagicalPark bot in PM: §7" + code);
        }
    }

    private void compareIp(Player player) {
        File file = new File("plugins/URPBot/auth/" + player.getUniqueId() + ".yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        String currentIp = PlayerIP.getIP(player);
        String oldIp = yml.getString("info.lastip");
        if(!currentIp.equalsIgnoreCase(oldIp)) {
            player.sendMessage("[URPBot] Your logging in from a different ip.");
            authenticate(player);
        }else {
            player.sendMessage("[URPBot] Your logging in from a known ip, nothing to worry about!");
        }

    }

    public void createIpFile(Player player, User user) {
        File file = new File("plugins/URPBot/auth/" + player.getUniqueId() + ".yml");
        if(file.exists())
            return;
        Bukkit.getLogger().info("[URPBot] Creating IP file for " + player.getName());
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        yml.set("info.discord", user.getIdAsString());
        yml.set("info.lastip", PlayerIP.getIP(player));
        ArrayList<String> list = new ArrayList<>();
        list.add(PlayerIP.getIP(player));
        yml.set("iphistory", list);
        auth.isRegistering.remove(player);
        auth.code.remove(player);
        player.sendMessage("[URPBot] Your account has been registered successfully!");
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveIp(Player player, String ip) {
        File file = new File("plugins/URPBot/auth/" + player.getUniqueId() + ".yml");
        if(!file.exists())
            return;
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        yml.set("info.lastip", ip);
        ArrayList<String> list = (ArrayList<String>) yml.getStringList("iphistory");
        list.add(ip);
        yml.set("iphistory", list);
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authenticate(Player player) {
        String code = RandomCode.generateCode(5);
        auth.code.put(player, code);
        player.sendMessage("[URPBot] You are not allowed to move! Your discord account is not verified yet! Please send the following code to the MagicalPark bot in PM: §7" + code);
    }

}
