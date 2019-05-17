package pl.grzegorz2047.anarchy;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.grzegorz2047.anarchy.chat.ChatFormatter;
import pl.grzegorz2047.anarchy.generator.RandomLocation;

import java.util.Arrays;
import java.util.Properties;

public class AnarchyGuide {
    private final Properties properties;

    public AnarchyGuide(Properties prop) {
        this.properties = prop;
    }

    public boolean isForCrackersons() {
        System.out.println(Arrays.toString(properties.values().toArray()));
        return Boolean.parseBoolean(properties.getProperty("isForCrackersow"));
    }

    public void startNewStory(Player player) {
        Location startingLocation = prepareSpawn(player);
        this.prepareStartingInventory(player);
        player.teleport(startingLocation);
        this.showTitle(player);
        player.sendMessage(ChatFormatter.formatChat(ChatColor.GRAY, "Użyj elytry, aby wylądować bepiecznie i rozpocząć nową historię!"));
    }

    public void restartStory(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location startingLocation = prepareSpawn(player);
        this.prepareStartingInventory(player);
        this.showTitle(player);
        event.setRespawnLocation(startingLocation);
    }

    private Location prepareSpawn(Player player) {
        Location startingLocation = RandomLocation.getStartingLocation(player.getWorld(), 5000, 250, 5000);
        this.generateStartingSurface(startingLocation, 3);
        return startingLocation;
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
        player.sendTitle("Poleć gdzie chcesz...", "Przetrwaj!", fadeIn, stay, fadeOut);
    }

    private void generateStartingSurface(Location startingLocation, int radius) {
        Location blockLocation = startingLocation.clone();
        blockLocation.setY(blockLocation.getBlockY() - 2);
        int blockX = blockLocation.getBlockX();
        int blockY = blockLocation.getBlockY();
        int blockZ = blockLocation.getBlockZ();
        World world = blockLocation.getWorld();
        for (int x = blockX - radius; x < blockX + radius; x++) {
            for (int z = blockZ - radius; z < blockZ + radius; z++) {
                Block spawnBlock = world.getBlockAt(x, blockY, z);
                spawnBlock.setType(Material.ACACIA_LEAVES);
            }
        }

    }

    public void serveFirstTimePlayers(Player player) {
        if (!player.hasPlayedBefore()) {
            this.startNewStory(player);
        }
    }


}