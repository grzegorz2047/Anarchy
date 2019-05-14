package pl.grzegorz2047.anarchy.chat;

import org.bukkit.ChatColor;

public class ChatFormatter {
    public static String formatChat(ChatColor textColor, String text) {
        return "[Anarchy] " + textColor + text;
    }
}
