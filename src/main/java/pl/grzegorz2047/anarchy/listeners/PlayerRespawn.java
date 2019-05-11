package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.grzegorz2047.anarchy.AnarchyGuide;

public class PlayerRespawn implements Listener {

    private final AnarchyGuide anarchyGuide;

    public PlayerRespawn(AnarchyGuide anarchyGuide) {
        this.anarchyGuide = anarchyGuide;
    }

    @EventHandler
    void onRespawn(PlayerRespawnEvent event) {

    }
}
