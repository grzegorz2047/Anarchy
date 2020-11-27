package pl.grzegorz2047.anarchy;

import org.bukkit.entity.Player;

public class OutsiderData {


    private final BreathingStrategy breathingStrategy;


    public OutsiderData(BreathingStrategy breathingStrategy) {
        this.breathingStrategy = breathingStrategy;
    }


    public float getRemainingTime() {
        return Math.max(breathingStrategy.getRemainingTime(), 0.0F);
    }

    public boolean keepBreath(Player player) {
        return breathingStrategy.keepBreath(player);
    }
}
