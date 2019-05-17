package pl.grzegorz2047.anarchy.listeners;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.anarchy.AnarchyGuide;

import java.util.List;

public class AuthMeLogin implements Listener {
    private final AnarchyGuide anarchyGuide;
    private final Plugin anarchy = Bukkit.getPluginManager().getPlugin("Anarchy");

    public AuthMeLogin(AnarchyGuide anarchyGuide) {
        this.anarchyGuide = anarchyGuide;
    }


    @EventHandler
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();
        List<MetadataValue> toRespawn = player.getMetadata("toRespawn");
        if(toRespawn.size() == 1){
            player.removeMetadata("toRespawn", anarchy);
            anarchyGuide.startNewStory(player);
        }else {
            anarchyGuide.serveFirstTimePlayers(player);

        }
        System.out.println("loginEvent");
    }
}
