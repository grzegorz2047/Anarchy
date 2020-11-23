package pl.grzegorz2047.anarchy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.grzegorz2047.anarchy.AnarchyGuide;
import pl.grzegorz2047.api.AntiLogout;

public class PlayerDeath implements Listener {
    private final AnarchyGuide anarchyGuide;
    private AntiLogout antiLogout;

    public PlayerDeath(AnarchyGuide anarchyGuide, AntiLogout antiLogout) {
        this.anarchyGuide = anarchyGuide;
        this.antiLogout = antiLogout;
    }

    @EventHandler
    private void onPlayerDie(PlayerDeathEvent event) {
        Player playerKilled = event.getEntity();
        Player killer = playerKilled.getKiller();
        this.antiLogout.clearFight(playerKilled, killer);
        //this.dbQuery.addDeathCount(playerKilled);
        //this.dbQuery.addKillCount(killer);
        //this.rank.updatePlayerRank();
        //anti logout clear
        //add death stats
        //Update rank
    }


}
