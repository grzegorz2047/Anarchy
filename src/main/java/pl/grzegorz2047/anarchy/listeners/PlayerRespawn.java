package pl.grzegorz2047.anarchy.listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.grzegorz2047.anarchy.AnarchyGuide;

public class PlayerRespawn implements Listener {

    private final AnarchyGuide anarchyGuide;
    private final PlayerBreathingManager playerBreathingManager;

    public PlayerRespawn(AnarchyGuide anarchyGuide, PlayerBreathingManager playerBreathingManager) {
        this.anarchyGuide = anarchyGuide;
        this.playerBreathingManager = playerBreathingManager;
    }

    @EventHandler
    void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        boolean forCrackersons = anarchyGuide.isForCrackersons();
        if (forCrackersons) {
            boolean authenticated = AuthMeApi.getInstance().isAuthenticated(player);
            if (!authenticated) {
                anarchyGuide.markToRespawn(player);
                return;
            }
        }
        anarchyGuide.restartStory(event);
        playerBreathingManager.respawnPlayer(player);
    }
}
