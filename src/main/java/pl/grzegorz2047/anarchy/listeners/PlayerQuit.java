package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.grzegorz2047.api.AntiLogout;

public class PlayerQuit implements Listener {

    private final AntiLogout antiLogout;

    public PlayerQuit(AntiLogout antiLogout) {
        this.antiLogout = antiLogout;
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        antiLogout.handleLogout(player);
    }
}
