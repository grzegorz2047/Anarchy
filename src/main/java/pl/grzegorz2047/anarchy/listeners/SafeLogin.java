package pl.grzegorz2047.anarchy.listeners;

import me.rerere.matrix.api.events.PlayerViolationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.grzegorz2047.api.Messages;

public class SafeLogin implements Listener {


    private Messages messages;
    private double lastSuccesfulAttempt;
    private final int MILISECONDS_COOLDOWN = 1000;

    @EventHandler
    private void preLogin(AsyncPlayerPreLoginEvent event) {
        long currentTime = System.currentTimeMillis();
        double difference = currentTime - this.lastSuccesfulAttempt;
        if (difference < MILISECONDS_COOLDOWN) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.messages.get("anarchy.login.manyLoggingPlayers").replace("%REMAIN%", String.valueOf(difference)));
        } else {
            this.lastSuccesfulAttempt = currentTime;
        }
    }

    /*@EventHandler
    private void onViolation(PlayerViolationEvent e) {
        Player player = e.getPlayer();
        String uuidStr = player.getUniqueId().toString();
        boolean isBedrock = uuidStr.contains("00000000-0000");
        //System.out.println(uuidStr + " porownano z 00000000-0000");
        if (isBedrock) {
           // System.out.println("Bypass violation for " + player.getName());
            e.setCancelled(true);
        }
    }*/


}
