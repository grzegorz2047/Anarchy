package pl.grzegorz2047.anarchy;

import org.bukkit.entity.Player;

import java.util.List;

public class StandardBreathing implements BreathingStrategy {
    private float timeToLoseBreath;
    private final WorldBuff worldBuff;
    public StandardBreathing(float remainingTimeToLoseBreath, List<WorldBuff> buffs) {
        this.timeToLoseBreath = remainingTimeToLoseBreath;
        this.worldBuff = buffs.get(0);
    }

    @Override
    public boolean keepBreath(Player player) {
        float decreaseAmount = 1;
        float weatherBuffValue = worldBuff.getWeatherBuffValue(player.getLocation());
        if(weatherBuffValue < decreaseAmount) {
            decreaseAmount = weatherBuffValue;
        }
        timeToLoseBreath -= decreaseAmount;

        return timeToLoseBreath > 0;
    }

    @Override
    public float getRemainingTime() {
        return timeToLoseBreath;
    }
}
