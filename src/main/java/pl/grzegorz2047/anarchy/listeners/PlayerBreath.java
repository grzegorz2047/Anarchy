package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.grzegorz2047.anarchy.events.SecondEvent;
import pl.grzegorz2047.anarchy.scoreboard.ScoreboardSidebar;

public class PlayerBreath implements Listener {

    private PlayerBreathingManager playerBreathingManager;

    public PlayerBreath(PlayerBreathingManager playerBreathingManager) {
        this.playerBreathingManager = playerBreathingManager;
    }



    @EventHandler
    public void onSecond(SecondEvent event) {
        Bukkit.getOnlinePlayers().forEach(x-> {
            Player player = x.getPlayer();
            Block block = player.getEyeLocation().getBlock();
            String name = player.getName();

            playerBreathingManager.dealFishBreathing(player, block, name);


        });
    }


}
