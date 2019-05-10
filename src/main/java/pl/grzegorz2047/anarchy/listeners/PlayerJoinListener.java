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

public class PlayerJoinListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPlayedBefore()) {
            Location startingLocation = RandomLocation.getStartingLocation(player.getWorld(), 5000, 250, 5000);
            generateStartingSurface(player, startingLocation, 3);
            player.teleport(startingLocation);
            PlayerInventory inventory = player.getInventory();
            inventory.setChestplate(new ItemStack(Material.ELYTRA, 1));

        }
    }

    private void generateStartingSurface(Player player, Location startingLocation, int radius) {
        Location blockLocation = startingLocation.clone();
        blockLocation.setY(blockLocation.getBlockY() - 1);
        int blockX = blockLocation.getBlockX();
        int blockY = blockLocation.getBlockY();
        int blockZ = blockLocation.getBlockZ();
        for (int x = blockX - radius; x < blockX + radius; x++) {
            for (int z = blockZ - radius; z < blockZ + radius; z++) {
                Block spawnBlock = player.getWorld().getBlockAt(x, blockY, z);
                spawnBlock.setType(Material.ACACIA_LEAVES);
            }
        }

    }

}
