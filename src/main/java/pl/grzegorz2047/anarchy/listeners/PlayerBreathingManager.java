package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.grzegorz2047.anarchy.OutsiderData;
import pl.grzegorz2047.anarchy.scoreboard.ScoreboardSidebar;

import java.util.HashMap;
import java.util.Map;

public class PlayerBreathingManager {
    private ScoreboardSidebar scoreboard = new ScoreboardSidebar();

    final Map<String, OutsiderData> playerOutside = new HashMap<String, OutsiderData>();

    public PlayerBreathingManager() {
    }

    void dealFishBreathing(Player player, Block block, String name) {
        if (block.isLiquid()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, Integer.MAX_VALUE));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, Integer.MAX_VALUE));
            playerOutside.remove(name);
        } else {
            OutsiderData o = playerOutside.get(name);
            if (o == null) {
                o = new OutsiderData(System.currentTimeMillis(), false);
                playerOutside.put(name, o);
            }
            o.setOutsideTime(System.currentTimeMillis());
            if (o.isOutOfAir()) {
                player.damage(0.5);
            }
            scoreboard.updateBreathingTime(player, o.getRemainingTime());
        }
    }

    public void respawnPlayer(Player player) {
        playerOutside.put(player.getName(), new OutsiderData(System.currentTimeMillis(), true));
    }
}