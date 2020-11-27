package pl.grzegorz2047.anarchy;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BreathingWithEchantedHelmet implements BreathingStrategy {
    private final float decreaseAmountForEchantedHelmet;
    private final WorldBuff worldBuff;
    private float timeToLoseBreath;

    public BreathingWithEchantedHelmet(float decreaseAmountForEchantedHelmet, float timeToLoseBreath, List<WorldBuff> buffs) {
        this.decreaseAmountForEchantedHelmet = decreaseAmountForEchantedHelmet;
        this.timeToLoseBreath = timeToLoseBreath;
        this.worldBuff = buffs.get(0);
    }

    @Override
    public boolean keepBreath(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        float decreaseAmount = 1;
        if(helmet != null) {
            if (helmet.containsEnchantment(Enchantment.OXYGEN)) {
                decreaseAmount = decreaseAmountForEchantedHelmet;
            }
        }
        float weatherBuff = worldBuff.getWeatherBuffValue(player.getLocation());
        if(buffIsMorePreferable(decreaseAmount, weatherBuff)) {
            decreaseAmount = weatherBuff;
        }

        timeToLoseBreath -= decreaseAmount;
        return timeToLoseBreath > 0;
    }


    private boolean buffIsMorePreferable(float decreaseAmount, float weatherBuff) {
        return weatherBuff < decreaseAmount;
    }

    @Override
    public float getRemainingTime() {
        return  timeToLoseBreath;
    }
}
