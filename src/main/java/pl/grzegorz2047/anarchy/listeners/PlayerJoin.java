package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.grzegorz2047.anarchy.AnarchyGuide;

public class PlayerJoin implements Listener {


    private final AnarchyGuide anarchyGuide;

    public PlayerJoin(AnarchyGuide anarchyGuide) {
        this.anarchyGuide = anarchyGuide;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        anarchyGuide.serveFirstTimePlayers(player);
        anarchyGuide.configurePlayer(player);
        System.out.println("joinEvent");
    }



}
