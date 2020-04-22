package pl.grzegorz2047.anarchy;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import pl.grzegorz2047.anarchy.chat.ChatFormatter;
import pl.grzegorz2047.anarchy.generator.RandomLocation;
import pl.grzegorz2047.anarchy.scoreboard.ScoreboardSidebar;

import java.util.*;

public class AnarchyGuide {
    private final Properties properties;
    private List<UUID> respawnMark = new ArrayList<>();
    private ScoreboardSidebar scoreboard = new ScoreboardSidebar();

    public AnarchyGuide(Properties prop) {
        this.properties = prop;
    }

    public boolean isForCrackersons() {
        return Boolean.parseBoolean(properties.getProperty("isForCrackersow"));
    }

    private void startNewStory(Player player) {
        Location startingLocation = prepareSpawn();
        this.prepareStartingInventory(player);
        player.teleport(startingLocation);
        this.showTitle(player);
        sendStartingMessage(player);
    }

    private void sendStartingMessage(Player player) {
        player.sendMessage(ChatFormatter.formatChat(ChatColor.GRAY, "Użyj elytry, aby wylądować bepiecznie i rozpocząć nową historię!"));
    }

    public void restartStory(PlayerRespawnEvent event) {
        Location originalRespawnLocation = event.getRespawnLocation();
        Player player = event.getPlayer();
        if (hasBedSpawn(originalRespawnLocation, player)) return;
        Location startingLocation = prepareSpawn();
        this.prepareStartingInventory(player);
        this.showTitle(player);
        sendStartingMessage(player);
        event.setRespawnLocation(startingLocation);

    }

    private boolean hasBedSpawn(Location originalRespawnLocation, Player player) {
        Location worldSpawnLocation = player.getBedSpawnLocation();
        if(worldSpawnLocation == null) {
            return false;
        }
        double originalX = originalRespawnLocation.getX();
        double originalY = originalRespawnLocation.getY();
        double originalZ = originalRespawnLocation.getZ();

        double worldX = worldSpawnLocation.getX();
        double worldY = worldSpawnLocation.getY();
        double worldZ = worldSpawnLocation.getZ();

        double v = originalX - worldX + originalY - worldY + originalZ - worldZ;
        if (v < 5) {
            return true;
        }
        return false;
    }

    private Location prepareSpawn() {
        Location startingLocation = RandomLocation.getStartingLocation(Bukkit.getWorlds().get(0), getStartingLocationRange(), getStartingLocationHeight(), getStartingLocationRange());
        this.generateStartingSurface(startingLocation, 3);
        return startingLocation;
    }


    private void prepareStartingInventory(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.setChestplate(new ItemStack(Material.ELYTRA, 1));
        ItemStack itemStack = createBook();
        inventory.setItem(0, itemStack);
    }

    private ItemStack createBook() {
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) itemStack.getItemMeta();
        itemMeta.setLore(new ArrayList<>());
        itemMeta.setTitle("Ja nie nuubić startu?");
        itemMeta.addPage("Najpierw użyj normalnie elytry.\n\nPotem wypadałoby nie upaść na twarz.\n\nPrzetrwaj lub schroń się na coordach -160, 64, -330");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
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
            for (int y = blockY - 1; y < blockY + 1; y++) {
                for (int z = blockZ - radius; z < blockZ + radius; z++) {
                    Block spawnBlock = world.getBlockAt(x, y, z);
                    spawnBlock.setType(Material.OAK_LEAVES);
                }
            }
        }

    }

    public void serveFirstTimePlayers(Player player) {
        if (!player.hasPlayedBefore()) {
            this.startNewStory(player);
        }
    }


    public void serveKrakerRespawn(Player player) {
        boolean removed = this.respawnMark.remove(player.getUniqueId());
        if (removed) {
            this.startNewStory(player);
        }
    }

    public void markToRespawn(Player player) {
        this.respawnMark.add(player.getUniqueId());
    }

    public int getStartingLocationRange() {
        return Integer.parseInt(properties.getProperty("startingLocationRange"));
    }

    public int getStartingLocationHeight() {
        return Integer.parseInt(properties.getProperty("startingLocationHeight"));
    }

    public void configurePlayer(Player player) {
        scoreboard.addWholeScoreboard(player);
    }

    public boolean useAnarchyChatHandler() {
        return Boolean.parseBoolean(properties.getProperty("useAnarchyChatHandler"));

    }
}