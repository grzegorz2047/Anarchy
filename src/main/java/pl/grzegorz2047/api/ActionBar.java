package pl.grzegorz2047.api;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.api.antilogout.Fight;

public class ActionBar {

    public static void sendActionBar(Player p, String message) {
        try {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
        } catch (Exception ex) {
            p.sendMessage(message);
        }
    }


}
