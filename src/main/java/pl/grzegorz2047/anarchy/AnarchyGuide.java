package pl.grzegorz2047.anarchy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.grzegorz2047.anarchy.generator.RandomLocation;
import pl.grzegorz2047.anarchy.listeners.PlayerJoinListener;

public class AnarchyGuide {


    public void startNewStory(Player player) {
        Location startingLocation = RandomLocation.getStartingLocation(player.getWorld(), 5000, 250, 5000);
        this.generateStartingSurface(player, startingLocation, 3);
        player.teleport(startingLocation);
        this.prepareStartingInventory(player);
        this.showTitle(player);
    }


    private void prepareStartingInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.setChestplate(new ItemStack(Material.ELYTRA, 1));
        ItemStack stackOfLeaves = new ItemStack(Material.JUNGLE_LEAVES, 64);
        inventory.addItem(stackOfLeaves);
        inventory.addItem(stackOfLeaves);
    }

    private void showTitle(Player player) {
        int fadeIn = 10;
        int stay = 8 * 20;
        int fadeOut = 20;
        player.sendTitle("Poleć gdzie chcesz", "Przetrwaj!", fadeIn, stay, fadeOut);
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