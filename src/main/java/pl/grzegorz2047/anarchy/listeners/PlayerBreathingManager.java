package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.grzegorz2047.anarchy.*;
import pl.grzegorz2047.anarchy.scoreboard.ScoreboardSidebar;

import java.util.*;

public class PlayerBreathingManager {
    private final Properties prop;
    private final boolean breathingEnchantedBreathingHelmetEnabled;
    private final float timeToTakeawayBreathPointWithEnchantedHelmet;
    private final float maxOutsideTimeWithoutWater;
    private final float maxOutsideTimeWithoutWaterAfterRespawn;
    private final boolean weatherBuffEnabled;
    private float weatherBuffBreathingDecreaseValue;
    private ScoreboardSidebar scoreboard = new ScoreboardSidebar();

    final Map<String, OutsiderData> playerOutside = new HashMap<>();

    public PlayerBreathingManager(Properties prop) {
        this.prop = prop;
        this.breathingEnchantedBreathingHelmetEnabled = Boolean.parseBoolean(String.valueOf(prop.get("breathingEnchantedBreathingHelmetEnabled")));
        this.timeToTakeawayBreathPointWithEnchantedHelmet = Float.parseFloat(String.valueOf(prop.get("timeToTakeawayBreathPointWithEnchantedHelmet")));
        this.maxOutsideTimeWithoutWater = Float.parseFloat(String.valueOf(prop.get("maxOutsideTimeWithoutWater")));
        this.maxOutsideTimeWithoutWaterAfterRespawn = Float.parseFloat(String.valueOf(prop.get("maxOutsideTimeWithoutWaterAfterRespawn")));
        this.weatherBuffBreathingDecreaseValue = Float.parseFloat(String.valueOf(prop.get("weatherBuffBreathingDecreaseValue")));
        this.weatherBuffEnabled = Boolean.parseBoolean(String.valueOf(prop.get("weatherBuffEnabled")));
        if (!this.weatherBuffEnabled) {
            weatherBuffBreathingDecreaseValue = 1.0F;
        }
    }

    void playerBreath(Player player, Block block, String name) {
        if (block.isLiquid()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, Integer.MAX_VALUE));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, Integer.MAX_VALUE));
            playerOutside.remove(name);
        } else {
            OutsiderData o = getOutsiderData(name, false);
            boolean canKeepBreath = o.keepBreath(player);
            if (!canKeepBreath) {
                player.damage(0.5);
            }
            scoreboard.updateBreathingTime(player, o.getRemainingTime());
        }
    }


    private OutsiderData getOutsiderData(String name, boolean firstTime) {
        OutsiderData o = playerOutside.get(name);
        if (o == null) {
            float timeToLoseBreath = maxOutsideTimeWithoutWater;
            if (firstTime) {
                timeToLoseBreath = maxOutsideTimeWithoutWaterAfterRespawn;
            }
            BreathingStrategy breathingStrategy = getBreathingStrategy(timeToLoseBreath);
            o = new OutsiderData(breathingStrategy);
            playerOutside.put(name, o);
        }
        return o;
    }

    private BreathingStrategy getBreathingStrategy(float timeToLoseBreath) {
        BreathingStrategy breathingStrategy;
        List<WorldBuff> buffs = Arrays.asList(new WorldBuff(weatherBuffBreathingDecreaseValue));
        if (breathingEnchantedBreathingHelmetEnabled) {
            breathingStrategy = new BreathingWithEchantedHelmet(timeToTakeawayBreathPointWithEnchantedHelmet, timeToLoseBreath, buffs);
        } else {
            breathingStrategy = new StandardBreathing(timeToLoseBreath, buffs);
        }
        return breathingStrategy;
    }

    public void respawnPlayer(Player player) {
        String name = player.getName();
        playerOutside.put(name, getOutsiderData(name, true));
    }

    public void regainBreath(Player player) {
        playerOutside.put(player.getName(), getOutsiderData(player.getName(), false));
    }
}