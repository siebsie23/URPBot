package me.siebsie23.urpbot.utils;

import org.bukkit.entity.Player;

public class PlayerIP {

    public static String getIP(Player player) {
        return player.getAddress().getHostName();
    }

    public static int getPort(Player player) {
        return player.getAddress().getPort();
    }

}
