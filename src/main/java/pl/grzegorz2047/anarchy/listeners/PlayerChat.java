package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.grzegorz2047.anarchy.AnarchyGuide;

public class PlayerChat implements Listener {
    private final AnarchyGuide anarchyGuide;

    public PlayerChat(AnarchyGuide anarchyGuide) {
        this.anarchyGuide = anarchyGuide;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        String playerName = p.getName();

    }
}
