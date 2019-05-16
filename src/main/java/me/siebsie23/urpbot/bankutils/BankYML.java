package me.siebsie23.urpbot.bankutils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.user.User;

import java.io.File;
import java.util.UUID;

public class BankYML {

    public BankYML() {

    }

    public void registerUser(User user) {
        if(isRegistered(user))
            return;
        File file = new File("plugins/URPBot/users/" + user.getIdAsString() + ".yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        yml.set("users." + user.getIdAsString() + ".coins", 0);
    }

    public boolean isRegistered(User user) {
        File file = new File("plugins/URPBot/users/" + user.getIdAsString() + ".yml");
        return file.exists();
    }

    public boolean isRegistered(UUID uuid) {
        File file = new File("plugins/URPBot/users.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        for(String key : yml.getConfigurationSection("users").getKeys(false)) {
            if(key.contains(uuid.toString()))
                return true;
        }
        return false;
    }

}
