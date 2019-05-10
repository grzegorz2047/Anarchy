package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

class RandomLocation {

    private static Random random = new Random();
    private static int[] minusOrNot = new int[]{-1, 1};

    static Location getStartingLocation(World world, int xRange, int yPos, int zRange) {
        return new Location(world, generateRandomPosition(xRange), yPos, generateRandomPosition(zRange));
    }

    private static int generateRandomPosition(int range) {
        int isMinusVal = minusOrNot[random.nextInt(1)];
        return isMinusVal * random.nextInt(range);
    }

}
