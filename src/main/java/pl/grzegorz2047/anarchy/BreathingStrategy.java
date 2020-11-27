package pl.grzegorz2047.anarchy;

import org.bukkit.entity.Player;

public interface BreathingStrategy {

    boolean keepBreath(Player player);


    float getRemainingTime();
}
