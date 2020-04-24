package pl.grzegorz2047.api;

import org.bukkit.ChatColor;

import java.util.Properties;

public class Messages extends Properties {
    public String get(String msgPath) {
        return ChatColor.translateAlternateColorCodes('&', this.readMsgFromProperties(msgPath));
    }

    private String readMsgFromProperties(String msgPath) {
        String property = getProperty(msgPath);
        if(property == null) {
            return "brak wiadomosci :< path: " + msgPath;
        }
        return property;
    }
}
