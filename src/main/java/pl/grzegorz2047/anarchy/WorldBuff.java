package pl.grzegorz2047.anarchy;

import org.bukkit.Location;

public class WorldBuff {

    private final float weatherBuffTime;

    public WorldBuff(float weatherBuffTime) {
        this.weatherBuffTime = weatherBuffTime;
    }

    float getWeatherBuffValue(Location location) {
        boolean hasStorm = location.getWorld().hasStorm();
        if (!hasStorm) {
            return 1;
        }
        if (location.getBlock().getBiome().name().contains("DESERT")) {
            return 1;
        }
        if(location.getY() < location.getWorld().getHighestBlockAt(location).getY()) {
            return 1;
        }
        return weatherBuffTime;
    }
}