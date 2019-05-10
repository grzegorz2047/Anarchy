package pl.grzegorz2047.anarchy.listeners;


import org.bukkit.Location;
import org.bukkit.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class RandomLocationTest {

    @Test
    void whenPassingRangeGiveLocationInRangeAt250Ypos() {
        Location startingLocation = RandomLocation.getStartingLocation(mock(World.class), 500, 250, 500);
        double x = startingLocation.getX();
        assertTrue(x <= 500 && x >= -500);
        assertEquals(250, startingLocation.getY());
    }
}