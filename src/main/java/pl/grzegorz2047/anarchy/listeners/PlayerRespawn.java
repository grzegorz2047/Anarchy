package pl.grzegorz2047.anarchy.listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.MetadataValueAdapter;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.anarchy.AnarchyGuide;

public class PlayerRespawn implements Listener {

    private final AnarchyGuide anarchyGuide;
    private final Plugin anarchy = Bukkit.getPluginManager().getPlugin("Anarchy");

    public PlayerRespawn(AnarchyGuide anarchyGuide) {
        this.anarchyGuide = anarchyGuide;
    }

    @EventHandler
    void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        MetadataValueAdapter metadataValue = new MetadataValueAdapter(anarchy) {
            @Override
            public Object value() {
                return true;
            }

            @Override
            public void invalidate() {

            }
        };
        player.setMetadata("toRespawn", metadataValue);
        boolean forCrackersons = anarchyGuide.isForCrackersons();
        if (forCrackersons) {
            boolean authenticated = AuthMeApi.getInstance().isAuthenticated(player);
            if (!authenticated) {
                return;
            }
        }
        anarchyGuide.restartStory(event);
    }
}
